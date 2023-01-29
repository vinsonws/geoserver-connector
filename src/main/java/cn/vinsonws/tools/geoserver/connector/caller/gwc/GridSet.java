package cn.vinsonws.tools.geoserver.connector.caller.gwc;

import cn.vinsonws.tools.geoserver.connector.caller.AbstractCaller;

import java.util.Map;

public final class GridSet {

    public static final class GridSetsBuilder
        extends AbstractCaller.ExecutableBuilder<GridSetsBuilder>
        implements AbstractCaller.Get<Map<String, Object>> {
        static final String EXTEND_API = "/gridsets";

        GridSetsBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }

        public GridSetBuilder gridSet(String gridsetName) {
            return new GridSetBuilder(this, gridsetName);
        }
    }

    public static final class GridSetBuilder
        extends AbstractCaller.ExecutableBuilder<GridSetBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Put,
        AbstractCaller.Delete {

        GridSetBuilder(AbstractCaller.ExecutableBuilder<?> other, String gridsetName) {
            super(other);
            appendApi("/" + gridsetName);
        }

        public void update(Object o) {
            requestBody(o);
            this.PUT();
        }
    }
}
