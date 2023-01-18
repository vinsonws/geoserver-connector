package cn.vinsonws.tools.geoserver.connector.args;

import cn.vinsonws.tools.geoserver.connector.AsyncGeoserverClient;
import cn.vinsonws.tools.geoserver.connector.RestConstant;
import cn.vinsonws.tools.geoserver.connector.exception.GeoserverServiceFailedRuntimeException;
import cn.vinsonws.tools.geoserver.connector.util.HttpUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

    private Object requestBody;

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

    public Object getRequestBody() {
        return requestBody;
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

    public void setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
    }


    @Override
    public String toString() {
        return "AbstractArgs{" + "api='" + api + '\'' +
            ", method=" + method +
            ", parameters=" + parameters +
            ", headers=" + headers +
            ", requestBody=" + requestBody +
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
        if (!headers.equals(that.headers)) return false;
        return Objects.equals(requestBody, that.requestBody);
    }

    @Override
    public int hashCode() {
        int result = api != null ? api.hashCode() : 0;
        result = 31 * result + method.hashCode();
        result = 31 * result + parameters.hashCode();
        result = 31 * result + headers.hashCode();
        result = 31 * result + (requestBody != null ? requestBody.hashCode() : 0);
        return result;
    }

    public static class Builder<B extends Builder<B, A>, A extends AbstractCaller> {
        private String apiFormatter;

        private final Map<String, String> parameters = new HashMap<>();

        private final Map<String, String> headers = new HashMap<>();

        private Object requestBody;


        public Builder(String apiFormatter) {
            this.apiFormatter = apiFormatter;
        }

        public Builder(Builder<?, ?> other) {
            this.requestBody = null;
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

        @SuppressWarnings("unchecked")
        public B requestBody(Object requestBody) {
            this.requestBody = requestBody;
            return (B) this;
        }

        public Object getRequestBody() {
            return requestBody;
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
            args.setRequestBody(requestBody);
            return args;
        }

        @SuppressWarnings("unchecked")
        private A newInstance() {
            try {
                for (Constructor<?> constructor : this.getClass().getEnclosingClass().getDeclaredConstructors()) {
                    if (constructor.getParameterCount() == 0) {
                        return (A) constructor.newInstance();
                    }
                }

                throw new RuntimeException(this.getClass().getEnclosingClass() + " must have no argument constructor");
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Builder{");
            sb.append("api='").append(apiFormatter).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public static class ExecutableBuilder<B extends Builder<B, A>, A extends AbstractCaller>
        extends Builder<B, A>
        implements Executable<A> {
        private final AsyncGeoserverClient client;

        ExecutableBuilder(AsyncGeoserverClient client, String apiFormatter) {
            super(apiFormatter);
            this.client = client;
        }

        ExecutableBuilder(ExecutableBuilder<?, ?> other) {
            super(other);
            this.client = other.client;
        }

        @Override
        public AsyncGeoserverClient client() {
            return this.client;
        }
    }

    interface Executable<A extends AbstractCaller> {
        A build(RestConstant.Method method);

        AsyncGeoserverClient client();
    }

    interface Get<A extends AbstractCaller, R> extends Executable<A> {
        default A buildGetArgs() {
            return build(RestConstant.Method.GET);
        }

        default R fetch() {
            return GET();
        }

        default R GET() {
            try {
                return client().executeAsync(buildGetArgs())
                    .thenApply(response -> {
                        if (!HttpUtils.validateResponse(response)) {
                            throw new GeoserverServiceFailedRuntimeException(response.statusCode());
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
                if (e.getCause() instanceof GeoserverServiceFailedRuntimeException re) {
                    throw re;
                }
                throw new RuntimeException(e);
            }
        }
    }

    interface Post<A extends AbstractCaller> extends Executable<A> {
        default A buildPostArgs() {
            return build(RestConstant.Method.POST);
        }

        default void POST() {
            try {
                client().executeAsync(buildPostArgs())
                    .thenAccept(response -> {
                        if (!HttpUtils.validateResponse(response)) {
                            throw new GeoserverServiceFailedRuntimeException(response.statusCode());
                        }

                    }).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                if (e.getCause() instanceof GeoserverServiceFailedRuntimeException re) {
                    throw re;
                }
                throw new RuntimeException(e);
            }
        }
    }

    interface Put<A extends AbstractCaller> extends Executable<A> {
        default A buildPutArgs() {
            return build(RestConstant.Method.PUT);
        }

        default void PUT() {
            try {
                client().executeAsync(buildPutArgs())
                    .thenAccept(response -> {
                        if (!HttpUtils.validateResponse(response)) {
                            throw new GeoserverServiceFailedRuntimeException(response.statusCode());
                        }
                    }).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                if (e.getCause() instanceof GeoserverServiceFailedRuntimeException re) {
                    throw re;
                }
                throw new RuntimeException(e);
            }
        }
    }

    interface Delete<A extends AbstractCaller> extends Executable<A> {
        default A buildDeleteArgs() {
            return build(RestConstant.Method.DELETE);
        }

        default void remove() {
            this.DELETE();
        }

        default void DELETE() {
            try {
                client().executeAsync(buildDeleteArgs())
                    .thenAccept(response -> {
                        if (!HttpUtils.validateResponse(response)) {
                            throw new GeoserverServiceFailedRuntimeException(response.statusCode());
                        }
                    }).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                if (e.getCause() instanceof GeoserverServiceFailedRuntimeException re) {
                    throw re;
                }
                throw new RuntimeException(e);
            }
        }
    }
}