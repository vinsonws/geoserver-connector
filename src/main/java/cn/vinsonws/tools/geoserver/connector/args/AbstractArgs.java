package cn.vinsonws.tools.geoserver.connector.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.http.HttpRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Vinsonws
 */
public abstract class AbstractArgs {
    private final String api;

    private final Map<String, String> parameters = new HashMap<>();

    private final Map<String, String> headers = new HashMap<>();

    private Object requestBody;

    protected AbstractArgs(String api) {
        this.api = api;
    }

    public void addParameter(String name, String value) {
        parameters.put(Objects.requireNonNull(name), Objects.requireNonNull(value));
    }

    public void addHeader(String name, String value) {
        headers.put(Objects.requireNonNull(name), Objects.requireNonNull(value));
    }

    public String getApi() {
        return api;
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
            sb.append(entry.getKey()).append('=').append(entry.getValue()).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public void setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
    }

    /**
     * Customized
     */
    public abstract HttpRequest.BodyPublisher getRequestBody();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseArgs{");
        sb.append("api='").append(api).append('\'');
        sb.append(", parameters=").append(parameters);
        sb.append(", headers=").append(headers);
        if (requestBody != null) {
            sb.append(", requestBody=").append(requestBody);
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractArgs abstractArgs = (AbstractArgs) o;

        if (!api.equals(abstractArgs.api)) return false;
        if (!parameters.equals(abstractArgs.parameters)) return false;
        if (!headers.equals(abstractArgs.headers)) return false;
        return Objects.equals(requestBody, abstractArgs.requestBody);
    }

    @Override
    public int hashCode() {
        int result = api.hashCode();
        result = 31 * result + parameters.hashCode();
        result = 31 * result + headers.hashCode();
        result = 31 * result + (requestBody != null ? requestBody.hashCode() : 0);
        return result;
    }

    public static class Builder<B extends Builder<B, A>, A extends AbstractArgs> {
        private final String api;
        private final Map<String, String> parameters = new HashMap<>();
        private final Map<String, String> headers = new HashMap<>();
        private Object requestBody;

        protected Builder(String api) {
            this.api = api;
        }

        @SuppressWarnings("unchecked")
        public B parameter(String name, String value) {
            this.parameters.put(Objects.requireNonNull(name), Objects.requireNonNull(value));
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

        public A build() {
            A args = newInstance();
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                args.addParameter(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                args.addHeader(entry.getKey(), entry.getValue());
            }
            args.setRequestBody(requestBody);
            return args;
        }

        @SuppressWarnings("unchecked")
        private A newInstance() {
            try {
                Constructor<?> constructor = this.getClass().getEnclosingClass().getConstructor(String.class);
                return (A) constructor.newInstance(api);
            } catch (InstantiationException
                     | IllegalAccessException
                     | InvocationTargetException
                     | SecurityException
                     | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
