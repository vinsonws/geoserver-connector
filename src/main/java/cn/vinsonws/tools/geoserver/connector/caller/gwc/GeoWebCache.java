package cn.vinsonws.tools.geoserver.connector.caller.gwc;

import cn.vinsonws.tools.geoserver.connector.AsyncGeoserverClient;
import cn.vinsonws.tools.geoserver.connector.caller.AbstractCaller;

public final class GeoWebCache {

    public static class GeoWebCacheBuilder extends AbstractCaller.ExecutableBuilder<GeoWebCacheBuilder> {

        static final String EXTEND_API = "/gwc";

        public GeoWebCacheBuilder(AsyncGeoserverClient client) {
            super(client, EXTEND_API);
        }

        public Blobstore.BlobstoresBuilder blobstores() {
            return new Blobstore.BlobstoresBuilder(this.appendApi("/rest"));
        }

        public Bounds.BoundsBuilder bounds(String layer, String srs, String type) {
            return new Bounds.BoundsBuilder(this.appendApi("/rest"), layer, srs, type);
        }

        public DiskQuota.DiskQuotaBuilder diskQuota() {
            return new DiskQuota.DiskQuotaBuilder(this.appendApi("/rest"));
        }

        public Global.GlobalBuilder global() {
            return new Global.GlobalBuilder(this.appendApi("/rest"));
        }

        public GridSet.GridSetsBuilder gridSets() {
            return new GridSet.GridSetsBuilder(this.appendApi("/rest"));
        }

        public Index.IndexBuilder index() {
            return new Index.IndexBuilder(this.appendApi("/rest"));
        }

        public MassTruncate.MassTruncateBuilder massTruncate() {
            return new MassTruncate.MassTruncateBuilder(this.appendApi("/rest"));
        }

        public Seed.SeedsBuilder seeds() {
            return new Seed.SeedsBuilder(this.appendApi("/rest"));
        }

        public Statistics.StatisticsBuilder statistics() {
            return new Statistics.StatisticsBuilder(this.appendApi("/rest"));
        }

        public Layer.LayersBuilder layers() {
            return new Layer.LayersBuilder(this.appendApi("/rest"));
        }

        public MapService.MapServiceBuilder service() {
            return new MapService.MapServiceBuilder(this);
        }
    }
}
