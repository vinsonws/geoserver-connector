package cn.vinsonws.tools.geoserver.connector.caller.gwc;

import cn.vinsonws.tools.geoserver.connector.caller.AbstractCaller;

import java.util.Map;

public final class Blobstore {

    public static final class BlobstoresBuilder
        extends AbstractCaller.ExecutableBuilder<BlobstoresBuilder>
        implements AbstractCaller.Get<Map<String, Object>> {
        static final String EXTEND_API = "/blobstores";

        BlobstoresBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }

        public BlobstoreBuilder blobstore(String blobstore) {
            return new BlobstoreBuilder(this, blobstore);
        }
    }

    public static final class BlobstoreBuilder
        extends AbstractCaller.ExecutableBuilder<BlobstoreBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Put,
        AbstractCaller.Delete {
        BlobstoreBuilder(AbstractCaller.ExecutableBuilder<?> other, String blobstore) {
            super(other);
            appendApi("/" + blobstore);
        }
    }
}
