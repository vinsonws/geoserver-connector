package cn.vinsonws.tools.geoserver.connector.args;

import cn.vinsonws.tools.geoserver.connector.AsyncGeoserverClient;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class LoggingCaller extends AbstractCommonCaller implements WithBody.Json {

    private static final String API = "/rest/logging.json";

    public static Builder builder(AsyncGeoserverClient client) {
        return new Builder(client);
    }

    public static final class Builder extends AbstractCaller.ExecutableBuilder<Builder, LoggingCaller>
        implements AbstractCaller.Get<LoggingCaller, Map<String, Object>>, AbstractCaller.Post<LoggingCaller> {
        Builder(AsyncGeoserverClient client) {
            super(client, API);
        }
    }
}
