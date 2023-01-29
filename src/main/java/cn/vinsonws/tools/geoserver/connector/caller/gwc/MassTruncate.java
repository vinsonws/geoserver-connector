package cn.vinsonws.tools.geoserver.connector.caller.gwc;

import cn.vinsonws.tools.geoserver.connector.caller.AbstractCaller;
import cn.vinsonws.tools.geoserver.connector.caller.WithBodies;
import cn.vinsonws.tools.geoserver.connector.caller.WithBody;

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

        public void issue(Object o) {
            requestBody(o);
            this.POST();
        }
    }
}
