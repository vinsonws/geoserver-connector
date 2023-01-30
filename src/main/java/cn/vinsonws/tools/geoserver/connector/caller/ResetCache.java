package cn.vinsonws.tools.geoserver.connector.caller;

/**
 * @author Vinsonws
 */
public final class ResetCache {

    public static final class ResetCacheBuilder extends AbstractCaller.ExecutableBuilder<ResetCacheBuilder>
        implements AbstractCaller.Post {
        static final String EXTEND_API = "/reset";

        ResetCacheBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }

        public void clean() {
            this.create();
        }
    }
}
