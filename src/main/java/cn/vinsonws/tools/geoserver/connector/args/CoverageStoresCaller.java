package cn.vinsonws.tools.geoserver.connector.args;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class CoverageStoresCaller extends AbstractCommonCaller {
    static final String EXTEND_API = "/coveragestores";

    public static final class CoverageStoresBuilder
        extends ExecutableBuilder<CoverageStoresBuilder, CoverageStoresCaller>
        implements Get<CoverageStoresCaller, Map<String, Object>>,
        Post<CoverageStoresCaller> {
        CoverageStoresBuilder(ExecutableBuilder<?, ?> other) {
            super(other);
        }

        public StoreBuilder store(String store) {
            return new StoreBuilder(appendApi("/" + store));
        }
    }

    public static final class StoreBuilder extends ExecutableBuilder<StoreBuilder, CoverageStoresCaller>
        implements Get<CoverageStoresCaller, Map<String, Object>>,
        Put<CoverageStoresCaller>,
        Delete<CoverageStoresCaller> {
        StoreBuilder(CoverageStoresBuilder other) {
            super(other);
        }

        public MethodFormatBuilder methodFormat(String method, String format) {
            return new MethodFormatBuilder(appendApi("/" + method + "." + format));
        }

        public ResetBuilder reset() {
            return new ResetBuilder(appendApi("/reset"));
        }
    }


    public static final class MethodFormatBuilder extends ExecutableBuilder<MethodFormatBuilder, CoverageStoresCaller>
        implements Put<CoverageStoresCaller> {
        MethodFormatBuilder(StoreBuilder other) {
            super(other);
        }
    }

    public static final class ResetBuilder extends ExecutableBuilder<ResetBuilder, CoverageStoresCaller>
        implements Post<CoverageStoresCaller>, Put<CoverageStoresCaller> {
        ResetBuilder(StoreBuilder other) {
            super(other);
        }
    }
}
