package cn.vinsonws.tools.geoserver.connector.caller;

import cn.vinsonws.tools.geoserver.connector.util.Verification;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class CoverageStore {

    public static final class CoverageStoresBuilder
        extends AbstractCaller.ExecutableBuilder<CoverageStoresBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Post {
        static final String EXTEND_API = "/coveragestores";

        CoverageStoresBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }

        public StoreBuilder store(String store) {
            return new StoreBuilder(this, Verification.validateNotEmptyString("store", store));
        }
    }

    public static final class StoreBuilder extends AbstractCaller.ExecutableBuilder<StoreBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Put,
        AbstractCaller.Delete {
        StoreBuilder(CoverageStoresBuilder other, String store) {
            super(other);
            appendApi("/" + store);
        }

        public Unit.MethodFormatBuilder methodFormat(String method, String format) {
            return new Unit.MethodFormatBuilder(this,
                Verification.validateNotEmptyString("method", method),
                Verification.validateNotEmptyString("format", format));
        }

        public ResetCache.ResetCacheBuilder reset() {
            return new ResetCache.ResetCacheBuilder(this);
        }
    }
}
