package cn.vinsonws.tools.geoserver.connector.caller.gwc;

import cn.vinsonws.tools.geoserver.connector.caller.AbstractCaller;

import java.util.Map;

public final class Layer {
    public static final class LayersBuilder
        extends AbstractCaller.ExecutableBuilder<LayersBuilder>
        implements AbstractCaller.Get<Map<String, Object>> {
        static final String EXTEND_API = "/layers";

        LayersBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }

        public LayerBuilder layer(String layerName) {
            return new LayerBuilder(this, layerName);
        }
    }

    public static final class LayerBuilder
        extends AbstractCaller.ExecutableBuilder<LayerBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Put,
        AbstractCaller.Delete {

        LayerBuilder(AbstractCaller.ExecutableBuilder<?> other, String layerName) {
            super(other);
            appendApi("/" + layerName);
        }

        public void createOrUpdate(Object o) {
            requestBody(o);
            this.PUT();
        }
    }

}
