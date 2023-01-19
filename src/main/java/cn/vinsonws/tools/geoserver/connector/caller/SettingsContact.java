package cn.vinsonws.tools.geoserver.connector.caller;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class SettingsContact {


    public static final class Builder extends AbstractCaller.ExecutableBuilder<Builder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Post {
        private static final String EXTEND_API = "/settings/contact";

        Builder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }

        @Override
        public WithBody withPostBody() {
            return WithBodies.JSON(requestBody);
        }
    }
}
