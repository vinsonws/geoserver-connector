package cn.vinsonws.tools.geoserver.connector.caller;

import cn.vinsonws.tools.geoserver.connector.util.Verification;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class DataStore {
    public static final class DataStoresBuilder
        extends AbstractCaller.ExecutableBuilder<DataStoresBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Post {
        static final String EXTEND_API = "/datastores";

        DataStoresBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }

        public DataStoreBuilder dataStore(String name) {
            return new DataStoreBuilder(this, Verification.validateNotEmptyString("name", name));
        }
    }

    public static final class DataStoreBuilder
        extends AbstractCaller.ExecutableBuilder<DataStoreBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Put,
        AbstractCaller.Delete {
        DataStoreBuilder(AbstractCaller.ExecutableBuilder<?> other, String name) {
            super(other);
            appendApi("/" + name);
        }

        public ResetCache.ResetCacheBuilder reset() {
            return new ResetCache.ResetCacheBuilder(this);
        }
    }
}
