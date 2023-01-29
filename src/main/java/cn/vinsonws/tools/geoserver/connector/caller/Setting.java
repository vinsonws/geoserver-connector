package cn.vinsonws.tools.geoserver.connector.caller;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class Setting {


    public static final class SettingsBuilder extends AbstractCaller.ExecutableBuilder<SettingsBuilder>
        implements AbstractCaller.Get<Map<String, Object>>, AbstractCaller.Post {
        private static final String EXTEND_API = "/settings";

        SettingsBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }
    }
}
