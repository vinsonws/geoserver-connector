package cn.vinsonws.tools.geoserver.connector.args;

import cn.vinsonws.tools.geoserver.connector.AsyncGeoserverClient;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class SettingsCaller extends AbstractCommonCaller implements WithBody.Json {
    private static final String API = "/rest/settings";

    public static Builder builder(AsyncGeoserverClient client) {
        return new Builder(client);
    }

    public static final class Builder extends AbstractCaller.ExecutableBuilder<Builder, SettingsCaller>
        implements AbstractCaller.Get<SettingsCaller, Map<String, Object>>, AbstractCaller.Post<SettingsCaller> {
        Builder(AsyncGeoserverClient client) {
            super(client, API);
        }
    }
}
