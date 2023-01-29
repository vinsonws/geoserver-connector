package cn.vinsonws.tools.geoserver.connector.caller;

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

        public void create(String dataStore) {
            // todo args solution
            this.requestBody(Map.of("dataStore", dataStore));
            this.POST();

        }

        public DataStoreBuilder dataStore(String name) {
            return new DataStoreBuilder(this, name);
        }

        @Override
        public WithBody withPostBody() {
            return WithBodies.JSON(requestBody);
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

//        public void update(String dataStore) {
//            // todo args solution
//            this.requestBody(Map.of("dataStore", dataStore));
//            this.PUT();
//        }

        public ResetCache.ResetCacheBuilder reset() {
            return new ResetCache.ResetCacheBuilder(this);
        }
    }
}
