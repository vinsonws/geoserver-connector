package cn.vinsonws.tools.geoserver.connector.caller.gwc;

import cn.vinsonws.tools.geoserver.connector.caller.AbstractCaller;

import java.util.Map;

public final class DiskQuota {
    public static final class DiskQuotaBuilder
        extends AbstractCaller.ExecutableBuilder<DiskQuotaBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Put {
        static final String EXTEND_API = "/diskquota";

        DiskQuotaBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }
    }
}
