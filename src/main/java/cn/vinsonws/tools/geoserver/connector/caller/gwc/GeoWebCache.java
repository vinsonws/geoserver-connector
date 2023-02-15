package cn.vinsonws.tools.geoserver.connector.caller.gwc;

import cn.vinsonws.tools.geoserver.connector.AsyncGeoserverClient;
import cn.vinsonws.tools.geoserver.connector.caller.AbstractCaller;

public final class GeoWebCache {

    public static class GeoWebCacheBuilder extends AbstractCaller.ExecutableBuilder<GeoWebCacheBuilder> {

        static final String EXTEND_API = "/gwc/rest";

        public GeoWebCacheBuilder(AsyncGeoserverClient client) {
            super(client, EXTEND_API);
        }

        public Blobstore.BlobstoresBuilder blobstores() {
            return new Blobstore.BlobstoresBuilder(this);
        }

        public Bounds.BoundsBuilder bounds(String layer, String srs, String type) {
            return new Bounds.BoundsBuilder(this, layer, srs, type);
        }

        public DiskQuota.DiskQuotaBuilder diskQuota() {
            return new DiskQuota.DiskQuotaBuilder(this);
        }

        public Global.GlobalBuilder global() {
            return new Global.GlobalBuilder(this);
        }

        public GridSet.GridSetsBuilder gridSets() {
            return new GridSet.GridSetsBuilder(this);
        }

        public Index.IndexBuilder index() {
            return new Index.IndexBuilder(this);
        }

        public MassTruncate.MassTruncateBuilder massTruncate() {
            return new MassTruncate.MassTruncateBuilder(this);
        }

        public Seed.SeedsBuilder seeds() {
            return new Seed.SeedsBuilder(this);
        }

        public Statistics.StatisticsBuilder statistics() {
            return new Statistics.StatisticsBuilder(this);
        }

        public Layer.LayersBuilder layers() {
            return new Layer.LayersBuilder(this);
        }
    }
}
