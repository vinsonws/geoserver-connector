package cn.vinsonws.tools.geoserver.connector.args;

import cn.vinsonws.tools.geoserver.connector.AsyncGeoserverClient;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class SettingsContactCaller extends AbstractCommonCaller implements WithBody.Json {
    private static final String API = "/rest/settings/contact";

    public static Builder builder(AsyncGeoserverClient client) {
        return new Builder(client);
    }

    public static final class Builder extends ExecutableBuilder<Builder, SettingsContactCaller>
        implements Get<SettingsContactCaller, Map<String, Object>>, Post<SettingsContactCaller> {
        Builder(AsyncGeoserverClient client) {
            super(client, API);
        }
    }
}
