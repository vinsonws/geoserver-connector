package cn.vinsonws.tools.geoserver.connector.caller;

import cn.vinsonws.tools.geoserver.connector.AsyncGeoserverClient;
import cn.vinsonws.tools.geoserver.connector.caller.gwc.GeoWebCache;

/**
 * @author Vinsonws
 */
public final class Base {

    public static BaseBuilder base(AsyncGeoserverClient client) {
        return new BaseBuilder(client);
    }

    public static class BaseBuilder extends AbstractCaller.ExecutableBuilder<BaseBuilder> {

        static final String EXTEND_API = "/rest";

        BaseBuilder(AsyncGeoserverClient client) {
            super(client, EXTEND_API);
        }

        public GeoWebCache.GeoWebCacheBuilder gwc() {
            return new GeoWebCache.GeoWebCacheBuilder(super.client());
        }

        public Manifest.ManifestBuilder aboutManifest() {
            return new Manifest.ManifestBuilder(this);
        }

        public SystemStatus.SystemStatusBuilder aboutSystemStatus() {
            return new SystemStatus.SystemStatusBuilder(this);
        }

        public Status.Builder aboutStatus() {
            return new Status.Builder(this);
        }

        public Version.Builder aboutVersion() {
            return new Version.Builder(this);
        }

        public ResetCache.ResetCacheBuilder resetCache() {
            return new ResetCache.ResetCacheBuilder(this);
        }

        public Reload.ReloadBuilder reload() {
            return new Reload.ReloadBuilder(this);
        }

        public Fonts.FontsBuilder fonts() {
            return new Fonts.FontsBuilder(this);
        }

        public Logging.LoggingBuilder logging() {
            return new Logging.LoggingBuilder(this);
        }

        public Resource.ResourcesBuilder resources() {
            return new Resource.ResourcesBuilder(this);
        }

        public Workspace.WorkspacesBuilder workspaces() {
            return new Workspace.WorkspacesBuilder(this);
        }

        public Namespace.NamespacesBuilder namespaces() {
            return new Namespace.NamespacesBuilder(this);
        }
    }
}
