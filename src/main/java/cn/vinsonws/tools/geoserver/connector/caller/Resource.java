package cn.vinsonws.tools.geoserver.connector.caller;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class Resource {

    public static final class ResourcesBuilder extends AbstractCaller.ExecutableBuilder<ResourcesBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Put,
        AbstractCaller.Delete {
        private static final String EXTEND_API = "/resource";

        ResourcesBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
            parameter("operation", "default");
            parameter("format", "json");
        }

        public ResourceBuilder resource(String resource) {
            return new ResourceBuilder(this, resource);
        }

        @Override
        public WithBody withPutBody() {
            return WithBodies.JSON(requestBody);
        }
    }

    public static final class ResourceBuilder extends AbstractCaller.ExecutableBuilder<ResourceBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Put,
        AbstractCaller.Delete {

        ResourceBuilder(AbstractCaller.ExecutableBuilder<?> other, String resource) {
            super(other);
            appendApi("/" + resource);
        }

        @Override
        public WithBody withPutBody() {
            return WithBodies.JSON(requestBody);
        }
    }
}
