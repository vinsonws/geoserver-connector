package cn.vinsonws.tools.geoserver.connector.caller;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class Logging {

    public static final class LoggingBuilder extends AbstractCaller.ExecutableBuilder<LoggingBuilder>
        implements AbstractCaller.Get<Map<String, Object>>, AbstractCaller.Post {
        private static final String EXTEND_API = "/logging.json";

        LoggingBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }
    }
}
