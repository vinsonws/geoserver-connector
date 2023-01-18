package cn.vinsonws.tools.geoserver.connector.args;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class CoveragesCaller extends AbstractCommonCaller {
    static final String EXTEND_API = "/coverages";

    public static final class CoveragesBuilder
        extends ExecutableBuilder<CoveragesBuilder, CoveragesCaller>
        implements Get<CoveragesCaller, Map<String, Object>>,
        Post<CoveragesCaller> {
        CoveragesBuilder(ExecutableBuilder<?, ?> other) {
            super(other);
        }

        public void create(Object coverage) {
            this.requestBody(Map.of("coverage", coverage));
            this.POST();
        }

        public CoverageBuilder coverage(String coverage) {
            return new CoverageBuilder(appendApi("/" + coverage));
        }
    }

    public static final class CoverageBuilder
        extends ExecutableBuilder<CoverageBuilder, CoveragesCaller>
        implements Get<CoveragesCaller, Map<String, Object>>,
        Put<CoveragesCaller>,
        Delete<CoveragesCaller> {
        CoverageBuilder(ExecutableBuilder<?, ?> other) {
            super(other);
        }

//        public void update() todo

        public ResetCacheCaller.Builder reset() {
            return new ResetCacheCaller.Builder(appendApi(ResetCacheCaller.EXTEND_API));
        }
    }
}
