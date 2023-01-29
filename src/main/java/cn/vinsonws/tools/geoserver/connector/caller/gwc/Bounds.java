package cn.vinsonws.tools.geoserver.connector.caller.gwc;

import cn.vinsonws.tools.geoserver.connector.caller.AbstractCaller;

import java.util.Map;

public final class Bounds {
    public static final class BoundsBuilder
        extends AbstractCaller.ExecutableBuilder<Blobstore.BlobstoresBuilder>
        implements AbstractCaller.Get<Map<String, Object>> {
        static final String EXTEND_API = "/bounds";

        BoundsBuilder(AbstractCaller.ExecutableBuilder<?> other, String layer, String srs, String type) {
            super(other);
            appendApi(EXTEND_API + "/" + layer + "/" + srs + "/" + type);
        }
    }
}
