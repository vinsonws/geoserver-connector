package cn.vinsonws.tools.geoserver.connector.args;

import cn.vinsonws.tools.geoserver.connector.AsyncGeoserverClient;

/**
 * @author Vinsonws
 */
public final class ResetCacheCaller extends AbstractCommonCaller implements WithBody.Empty {

    static final String EXTEND_API = "/reset";

    public static Builder builder(AsyncGeoserverClient client) {
        return new Builder(client);
    }

    public static final class Builder extends ExecutableBuilder<Builder, ResetCacheCaller>
        implements Post<ResetCacheCaller> {
        Builder(AsyncGeoserverClient client) {
            super(client, EXTEND_API);
        }

        Builder(ExecutableBuilder<?, ?> other) {
            super(other);
        }

        public void clean() {
            this.POST();
        }
    }
}
