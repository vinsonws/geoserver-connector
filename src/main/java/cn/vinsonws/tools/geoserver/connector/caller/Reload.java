package cn.vinsonws.tools.geoserver.connector.caller;

/**
 * @author Vinsonws
 */
public final class Reload  {

    public static final class ReloadBuilder extends AbstractCaller.ExecutableBuilder<ReloadBuilder>
        implements AbstractCaller.Post {
        private static final String EXTEND_API = "/reload";

        ReloadBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }


    }
}
