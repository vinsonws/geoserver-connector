package cn.vinsonws.tools.geoserver.connector.args;

import cn.vinsonws.tools.geoserver.connector.AsyncGeoserverClient;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class VersionCaller extends AbstractCommonCaller {

    private static final String API = "/rest/about/version.json";

    public static Builder builder(AsyncGeoserverClient client) {
        return new Builder(client);
    }

    public static final class Builder extends AbstractCaller.ExecutableBuilder<Builder, VersionCaller>
        implements AbstractCaller.Get<VersionCaller, Map<String, Object>> {
        Builder(AsyncGeoserverClient client) {
            super(client, API);
        }
    }
}
