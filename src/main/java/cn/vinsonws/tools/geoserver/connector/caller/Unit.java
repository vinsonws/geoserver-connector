package cn.vinsonws.tools.geoserver.connector.caller;

/**
 * @author Vinsonws
 */
public final class Unit {
    public static final class MethodFormatBuilder extends AbstractCaller.ExecutableBuilder<MethodFormatBuilder>
        implements AbstractCaller.Put {
        MethodFormatBuilder(CoverageStore.StoreBuilder other, String method, String format) {
            super(other);
            appendApi("/" + method + "." + format);
        }

        @Override
        public WithBody withPutBody() {
            return WithBodies.JSON(requestBody);
        }
    }

}
