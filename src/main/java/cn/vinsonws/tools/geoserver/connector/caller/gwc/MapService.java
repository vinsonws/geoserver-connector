package cn.vinsonws.tools.geoserver.connector.caller.gwc;

import cn.vinsonws.tools.geoserver.connector.caller.AbstractCaller;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class MapService {

    public static final class MapServiceBuilder
        extends AbstractCaller.ExecutableBuilder<MapServiceBuilder> {
        static final String EXTEND_API = "/service";

        MapServiceBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }

        public TMSBuilder tms(String layerName, String gridSetName, String format) {
            return new TMSBuilder(this, layerName, gridSetName, format);
        }

        public WMTSBuilder wmts(String layerName, String gridSetName, String format) {
            return new WMTSBuilder(this, layerName, gridSetName, format);
        }
    }

    public static final class TMSBuilder
        extends AbstractCaller.ExecutableBuilder<TMSBuilder>
        implements AbstractCaller.Get<Map<String, Object>> {
        static final String EXTEND_API = "/tms/1.0.0";
        private final String format;

        TMSBuilder(AbstractCaller.ExecutableBuilder<?> other, String layerName, String gridSetName, String format) {
            super(other);
            appendApi(EXTEND_API + "/" + layerName + "@" + gridSetName + "@" + format);
            this.format = format;
        }


        public String url() {
            return buildGetArgs().getApi() + "/{z}/{x}/{reverseY}." + format;
        }
    }

    public static final class WMTSBuilder
        extends AbstractCaller.ExecutableBuilder<TMSBuilder> {
        static final String EXTEND_API = "/wmts";

        WMTSBuilder(AbstractCaller.ExecutableBuilder<?> other, String layerName, String gridSetName, String format) {
            super(other);
            appendApi(EXTEND_API);
            parameter("layer", layerName);
            parameter("tilematrixset", gridSetName);
            parameter("Service", "WMTS");
            parameter("Request", "GetTile");
            parameter("Version", "1.0.0");
            parameter("Format", format);
            parameter("TileMatrix", gridSetName + ":{TileMatrix}");
            parameter("TileCol", "{TileCol}");
            parameter("TileRow", "{TileRow}");
        }

        public String url() {
            return buildCaller().getApiWithParameters();
        }
    }
}
