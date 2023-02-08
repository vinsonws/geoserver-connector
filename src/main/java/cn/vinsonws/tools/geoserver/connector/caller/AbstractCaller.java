package cn.vinsonws.tools.geoserver.connector.caller;

import cn.vinsonws.tools.geoserver.connector.AsyncGeoserverClient;
import cn.vinsonws.tools.geoserver.connector.RestConstant;
import cn.vinsonws.tools.geoserver.connector.body.WithBodies;
import cn.vinsonws.tools.geoserver.connector.body.WithBody;
import cn.vinsonws.tools.geoserver.connector.exception.GeoserverServiceFailedRuntimeException;
import cn.vinsonws.tools.geoserver.connector.util.HttpUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * @author Vinsonws
 */
public abstract class AbstractCaller {
    private String api;

    private RestConstant.Method method = RestConstant.Method.GET;

    private final Map<String, String> parameters = new HashMap<>();

    private final Map<String, String> headers = new HashMap<>();

    public void addParameter(String name, String value) {
        parameters.put(Objects.requireNonNull(name), Objects.requireNonNull(value));
    }

    public void addHeader(String name, String value) {
        headers.put(Objects.requireNonNull(name), Objects.requireNonNull(value));
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public void setMethod(RestConstant.Method method) {
        this.method = method;
    }

    public RestConstant.Method getMethod() {
        return method;
    }

    public String getApiWithParameters() {
        String parameter;
        if ((parameter = this.getParameterString()).length() > 0) {
            return api + "?" + parameter;
        }
        return api;
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    public String getParameterString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : this.parameters.entrySet()) {
            sb.append(entry.getKey()).append('=').append(entry.getValue()).append('&');
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '&')
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }


    @Override
    public String toString() {
        return "AbstractArgs{" + "api='" + api + '\'' +
            ", method=" + method +
            ", parameters=" + parameters +
            ", headers=" + headers +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractCaller that = (AbstractCaller) o;

        if (!Objects.equals(api, that.api)) return false;
        if (method != that.method) return false;
        if (!parameters.equals(that.parameters)) return false;
        return headers.equals(that.headers);
    }

    @Override
    public int hashCode() {
        int result = api != null ? api.hashCode() : 0;
        result = 31 * result + method.hashCode();
        result = 31 * result + parameters.hashCode();
        result = 31 * result + headers.hashCode();
        return result;
    }

    public abstract static class Builder<B extends Builder<B, A>, A extends AbstractCaller> {
        private String apiFormatter;

        private final Map<String, String> parameters = new HashMap<>();

        private final Map<String, String> headers = new HashMap<>();

        public Builder(String apiFormatter) {
            this.apiFormatter = apiFormatter;
        }

        public Builder(Builder<?, ?> other) {
            this.parameters.putAll(other.parameters);
            this.headers.putAll(other.headers);
            this.apiFormatter = other.apiFormatter;
        }

        @SuppressWarnings("unchecked")
        public B parameter(String name, String value) {
            this.parameters.put(Objects.requireNonNull(name), Objects.requireNonNull(value));
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B appendApi(String v) {
            this.apiFormatter = this.apiFormatter + v;
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B header(String name, String value) {
            this.headers.put(Objects.requireNonNull(name), Objects.requireNonNull(value));
            return (B) this;
        }

        public A build(RestConstant.Method method) {
            A args = buildCaller();
            args.setMethod(method);
            return args;
        }

        public A buildCaller() {
            A args = newInstance();
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                args.addParameter(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                args.addHeader(entry.getKey(), entry.getValue());
            }
            args.setApi(apiFormatter);
            return args;
        }

        protected abstract A newInstance();

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Builder{");
            sb.append("api='").append(apiFormatter).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public static class ExecutableBuilder<B extends Builder<B, CommonCaller>>
        extends Builder<B, CommonCaller>
        implements Executable<CommonCaller> {
        private final AsyncGeoserverClient client;

        protected ExecutableBuilder(AsyncGeoserverClient client, String apiFormatter) {
            super(apiFormatter);
            this.client = client;
        }

        protected ExecutableBuilder(ExecutableBuilder<?> other) {
            super(other);
            this.client = other.client;
        }

        @Override
        public AsyncGeoserverClient client() {
            return this.client;
        }

        @Override
        protected CommonCaller newInstance() {
            return new CommonCaller();
        }
    }

    interface Executable<A extends AbstractCaller> {
        A build(RestConstant.Method method);

        AsyncGeoserverClient client();
    }

    public interface Get<R> extends Executable<CommonCaller> {
        default CommonCaller buildGetArgs() {
            return build(RestConstant.Method.GET);
        }

        default R fetch() {
            return GET();
        }

        default R GET() {
            try {
                return client().executeAsync(buildGetArgs(), null)
                    .thenApply(response -> {
                        if (!HttpUtils.validateResponse(response)) {
                            throw new GeoserverServiceFailedRuntimeException(response.statusCode(), response.uri().toString());
                        }
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            return mapper.readValue(response.body(), new TypeReference<R>() {
                            });
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                if (e.getCause() instanceof GeoserverServiceFailedRuntimeException) {
                    throw (GeoserverServiceFailedRuntimeException) e.getCause();
                }
                throw new RuntimeException(e);
            }
        }
    }

    public interface Post extends Executable<CommonCaller> {
        default CommonCaller buildPostArgs() {
            return build(RestConstant.Method.POST);
        }

        default void POST(WithBody withBody) {
            try {
                client().executeAsync(buildPostArgs(), withBody)
                    .thenAccept(response -> {
                        if (!HttpUtils.validateResponse(response)) {
                            throw new GeoserverServiceFailedRuntimeException(response.statusCode(), response.uri().toString());
                        }
                    }).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                if (e.getCause() instanceof GeoserverServiceFailedRuntimeException) {
                    throw (GeoserverServiceFailedRuntimeException) e.getCause();
                }
                throw new RuntimeException(e);
            }
        }

        default void create() {
            this.POST(WithBodies.EMPTY);
        }

        default void create(WithBody withBody) {
            this.POST(withBody);
        }
    }

    public interface Put extends Executable<CommonCaller> {
        default CommonCaller buildPutArgs() {
            return build(RestConstant.Method.PUT);
        }

        default void PUT(WithBody withBody) {
            try {
                client().executeAsync(buildPutArgs(), withBody)
                    .thenAccept(response -> {
                        if (!HttpUtils.validateResponse(response)) {
                            throw new GeoserverServiceFailedRuntimeException(response.statusCode(), response.uri().toString());
                        }
                    }).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                if (e.getCause() instanceof GeoserverServiceFailedRuntimeException) {
                    throw (GeoserverServiceFailedRuntimeException) e.getCause();
                }
                throw new RuntimeException(e);
            }
        }

        default void update() {
            this.PUT(WithBodies.EMPTY);
        }

        default void update(WithBody withBody) {
            this.PUT(withBody);
        }
    }

    public interface Delete extends Executable<CommonCaller> {
        default CommonCaller buildDeleteArgs() {
            return build(RestConstant.Method.DELETE);
        }

        default void remove() {
            this.DELETE();
        }

        default void DELETE() {
            try {
                client().executeAsync(buildDeleteArgs(), null)
                    .thenAccept(response -> {
                        if (!HttpUtils.validateResponse(response)) {
                            throw new GeoserverServiceFailedRuntimeException(response.statusCode(), response.uri().toString());
                        }
                    }).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                if (e.getCause() instanceof GeoserverServiceFailedRuntimeException) {
                    throw (GeoserverServiceFailedRuntimeException) e.getCause();
                }
                throw new RuntimeException(e);
            }
        }
    }
}