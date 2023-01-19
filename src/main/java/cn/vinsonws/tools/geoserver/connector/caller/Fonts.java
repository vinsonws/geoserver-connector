package cn.vinsonws.tools.geoserver.connector.caller;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class Fonts {

    public static final class FontsBuilder extends AbstractCaller.ExecutableBuilder<FontsBuilder>
        implements AbstractCaller.Get<Map<String, Object>> {
        private static final String EXTEND_API = "/fonts.json";

        FontsBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }
    }
}
