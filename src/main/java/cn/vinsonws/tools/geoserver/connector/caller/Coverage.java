package cn.vinsonws.tools.geoserver.connector.caller;

import cn.vinsonws.tools.geoserver.connector.util.Verification;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class Coverage {

    public static final class CoveragesBuilder
        extends AbstractCaller.ExecutableBuilder<CoveragesBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Post {
        static final String EXTEND_API = "/coverages";

        CoveragesBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }

        public CoverageBuilder coverage(String coverage) {
            return new CoverageBuilder(this, Verification.validateNotEmptyString("coverage", coverage));
        }
    }

    public static final class CoverageBuilder
        extends AbstractCaller.ExecutableBuilder<CoverageBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Put,
        AbstractCaller.Delete {
        CoverageBuilder(AbstractCaller.ExecutableBuilder<?> other, String name) {
            super(other);
            appendApi("/" + name);
        }

        public ResetCache.ResetCacheBuilder reset() {
            return new ResetCache.ResetCacheBuilder(this);
        }

        @Override
        public void remove() {
            this.remove(true);
        }


        public void remove(boolean recurse) {
            this.parameter("recurse", String.valueOf(recurse));
            AbstractCaller.Delete.super.remove();
        }
    }
}
