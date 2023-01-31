package cn.vinsonws.tools.geoserver.connector.body;

import cn.vinsonws.tools.geoserver.connector.util.Verification;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Vinsonws
 */
public class CreateDatastore implements WithBody.Json {
    String name;
    final Map<String, Object> connectionParameters = new HashMap<>();


    @Override
    public void validate() {
        Verification.validateNotEmptyString("name", name);
    }

    @Override
    public Object getRequestBody() {
        return Map.of("dataStore", Map.of(
            "name", name,
            "connectionParameters", Map.of("entry",
                connectionParameters.entrySet().stream()
                    .map(entry -> Map.of("@key", entry.getKey(), "$", entry.getValue()))
                    .collect(Collectors.toList())
            )));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder
        extends WithBody.Builder<Builder, CreateDatastore> {
        public Builder name(String name) {
            operations.add(body -> body.name = name);
            return this;
        }

        public Builder connectionParameter(String key, Object value) {
            operations.add(body -> body.connectionParameters.put(key, value));
            return this;
        }
    }
}
