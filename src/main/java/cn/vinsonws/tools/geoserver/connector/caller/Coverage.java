package cn.vinsonws.tools.geoserver.connector.caller;

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

        public void create(Object coverage) {
            this.requestBody(Map.of("coverage", coverage));
            this.POST();
        }

        public CoverageBuilder coverage(String coverage) {
            return new CoverageBuilder(this, coverage);
        }

        @Override
        public WithBody withPostBody() {
            return WithBodies.JSON(requestBody);
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

//        public void update() todo

        public ResetCache.ResetCacheBuilder reset() {
            return new ResetCache.ResetCacheBuilder(this);
        }

        @Override
        public WithBody withPutBody() {
            return WithBodies.JSON(requestBody);
        }
    }
}
