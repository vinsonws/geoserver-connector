package cn.vinsonws.tools.geoserver.connector.caller.gwc;

import cn.vinsonws.tools.geoserver.connector.caller.AbstractCaller;
import cn.vinsonws.tools.geoserver.connector.caller.WithBodies;
import cn.vinsonws.tools.geoserver.connector.caller.WithBody;

import java.util.Map;

public final class Global {
    public static final class GlobalBuilder
        extends AbstractCaller.ExecutableBuilder<GlobalBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Put {
        static final String EXTEND_API = "/global";

        GlobalBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }

        public void update(Object o) {
            requestBody(o);
            this.PUT();
        }
    }
}
