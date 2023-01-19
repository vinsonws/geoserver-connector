package cn.vinsonws.tools.geoserver.connector.caller;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class Status {

    public static final class Builder extends AbstractCaller.ExecutableBuilder<Builder>
        implements AbstractCaller.Get<Map<String, Object>> {
        private static final String EXTEND_API = "/about/status.json";

        Builder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }
    }
}
