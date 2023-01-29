package cn.vinsonws.tools.geoserver.connector.caller.gwc;

import cn.vinsonws.tools.geoserver.connector.caller.AbstractCaller;

import java.util.Map;

public final class Seed {
    public static final class SeedsBuilder
        extends AbstractCaller.ExecutableBuilder<SeedsBuilder>
        implements AbstractCaller.Get<Map<String, Object>> {
        static final String EXTEND_API = "/seed";

        SeedsBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }

        @Override
        public Map<String, Object> fetch() {
            return appendApi(".json").GET();
        }

        public SeedBuilder seed(String layer, String format) {
            return new SeedBuilder(this, layer, format);
        }
    }

    public static final class SeedBuilder
        extends AbstractCaller.ExecutableBuilder<SeedBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Post {

        SeedBuilder(AbstractCaller.ExecutableBuilder<?> other, String layer, String format) {
            super(other);
            appendApi("/" + layer + "." + format);
        }
    }
}
