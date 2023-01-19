package cn.vinsonws.tools.geoserver.connector.caller;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class SystemStatus {

    public static final class SystemStatusBuilder extends AbstractCaller.ExecutableBuilder<SystemStatusBuilder>
        implements AbstractCaller.Get<Map<String, Object>> {
        private static final String EXTEND_API = "/about/system-status.json";

        SystemStatusBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }
    }
}
