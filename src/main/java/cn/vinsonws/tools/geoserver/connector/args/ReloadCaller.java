package cn.vinsonws.tools.geoserver.connector.args;

import cn.vinsonws.tools.geoserver.connector.AsyncGeoserverClient;

/**
 * @author Vinsonws
 */
public final class ReloadCaller extends AbstractCommonCaller implements WithBody.Empty{

    private static final String API = "/rest/reload";

    public static Builder builder(AsyncGeoserverClient client) {
        return new Builder(client);
    }

    public static final class Builder extends AbstractCaller.ExecutableBuilder<Builder, ReloadCaller>
        implements AbstractCaller.Post<ReloadCaller> {
        Builder(AsyncGeoserverClient client) {
            super(client, API);
        }
    }
}
