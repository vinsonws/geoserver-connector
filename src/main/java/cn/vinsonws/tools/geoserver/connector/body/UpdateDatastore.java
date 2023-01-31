package cn.vinsonws.tools.geoserver.connector.body;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Vinsonws
 */
public class UpdateDatastore implements WithBody.Json {
    String description;
    Boolean enabled;
    Boolean _default;
    final Map<String, Object> connectionParameters = new HashMap<>();

    @Override
    public void validate() {

    }

    @Override
    public Object getRequestBody() {
        Map<String, Object> map = new HashMap<>();
        if (description != null)
            map.put("description", description);
        if (enabled != null) {
            map.put("enabled", enabled ? "true" : "false");
        }
        if (_default != null) {
            map.put("_default", _default ? "true" : "false");
        }
        map.put("connectionParameters", Map.of("entry",
            connectionParameters.entrySet().stream()
                .map(entry -> Map.of("@key", entry.getKey(), "$", entry.getValue()))
                .collect(Collectors.toList())
        ));
        return Map.of("dataStore", map);
    }

    public static final class Builder
        extends WithBody.Builder<Builder, UpdateDatastore> {
        public Builder description(String description) {
            operations.add(body -> body.description = description);
            return this;
        }

        public Builder enabled(boolean enabled) {
            operations.add(body -> body.enabled = enabled);
            return this;
        }

        public Builder _default(boolean _default) {
            operations.add(body -> body._default = _default);
            return this;
        }

        public Builder connectionParameter(String key, Object value) {
            operations.add(body -> body.connectionParameters.put(key, value));
            return this;
        }
    }
}
