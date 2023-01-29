package cn.vinsonws.tools.geoserver.connector.caller.gwc;

import cn.vinsonws.tools.geoserver.connector.caller.AbstractCaller;

public final class Index {
    public static final class IndexBuilder
        extends AbstractCaller.ExecutableBuilder<IndexBuilder>
        implements AbstractCaller.Get<Object> {
        static final String EXTEND_API = "/rest";

        IndexBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }
    }
}
