package cn.vinsonws.tools.geoserver.connector.args;

import cn.vinsonws.tools.geoserver.connector.AsyncGeoserverClient;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class ResourceCaller extends AbstractCommonCaller {
    private static final String API = "/rest/resource";

    public static Builder builder(AsyncGeoserverClient client) {
        return new Builder(client);
    }

    public static final class Builder extends AbstractCaller.ExecutableBuilder<Builder, ResourceCaller>
        implements AbstractCaller.Get<ResourceCaller, Map<String, Object>>,
        AbstractCaller.Put<ResourceCaller>,
        AbstractCaller.Delete<ResourceCaller> {
        Builder(AsyncGeoserverClient client) {
            super(client, API);
            parameter("operation", "default");
            parameter("format", "json");
        }

        public ResourceBuilder resource(String resource) {
            return new ResourceBuilder(appendApi("/" + resource));
        }
    }

    public static final class ResourceBuilder extends AbstractCaller.ExecutableBuilder<ResourceBuilder, ResourceCaller>
        implements AbstractCaller.Get<ResourceCaller, Map<String, Object>>,
        AbstractCaller.Put<ResourceCaller>,
        AbstractCaller.Delete<ResourceCaller> {

        ResourceBuilder(ExecutableBuilder<?, ?> other) {
            super(other);
        }
    }
}
