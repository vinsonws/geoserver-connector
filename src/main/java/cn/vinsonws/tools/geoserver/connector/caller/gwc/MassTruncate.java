package cn.vinsonws.tools.geoserver.connector.caller.gwc;

import cn.vinsonws.tools.geoserver.connector.caller.AbstractCaller;

import java.util.Map;

public final class MassTruncate {
    public static final class MassTruncateBuilder
        extends AbstractCaller.ExecutableBuilder<MassTruncateBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Post {
        static final String EXTEND_API = "/masstruncate";

        MassTruncateBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }

    }
}
