package cn.vinsonws.tools.geoserver.connector;

import cn.vinsonws.tools.geoserver.connector.caller.*;
import cn.vinsonws.tools.geoserver.connector.caller.gwc.GeoWebCache;
import cn.vinsonws.tools.geoserver.connector.caller.gwc.MapService;

import java.util.Base64;

/**
 * @author Vinsonws
 */
public class GeoserverConnector {

    private final AsyncGeoserverClient asyncClient;
    private final Base.BaseBuilder base;

    public GeoserverConnector(String baseurl, String userName, String password) {
        this.asyncClient =
            new AsyncGeoserverClient(baseurl,
                "Basic " + Base64.getEncoder().encodeToString((userName + ":" + password).getBytes()));
        this.base = Base.base(this.asyncClient);
    }

    /**
     * This endpoint retrieves details on all loaded JARs. All the GeoServer manifest JARs
     * are marked with the property GeoServerModule and classified by type, so you can use
     * filtering capabilities to search for a set of manifests using regular expressions
     * (see the manifest parameter) or a type category (see the key and value parameter).
     * <br>
     * <br>
     * Use the “Accept:” header to specify format or append an extension to the endpoint
     * (example “/about/manifest.xml” for XML).
     * <br>
     * The model is very simple and is shared between the version and the resource requests
     * to parse both requests. You can customize the results adding a properties file called
     * manifest.properties into the root of the data directory.
     *
     * @return Manifest.ManifestBuilder
     */
    public Manifest.ManifestBuilder aboutManifest() {
        return this.base.aboutManifest();
    }

    /**
     * Returns a list of system-level information. Major operating systems
     * (Linux, Windows and MacOX) are supported out of the box.
     *
     * @return SystemStatus.SystemStatusBuilder
     */
    public SystemStatus.SystemStatusBuilder aboutSystemStatus() {
        return this.base.aboutSystemStatus();
    }

    /**
     * This endpoint shows the status details of all installed and configured modules.
     * Status details always include human readable name, and module name.
     * Optional details include version, availability, status message, and links to documentation.
     *
     * @return Status.Builder
     */
    public Status.Builder aboutStatus() {
        return this.base.aboutStatus();
    }

    /**
     * This endpoint shows only the details for the high-level components: GeoServer, GeoTools, and GeoWebCache.
     *
     * @return Version.Builder
     */
    public Version.Builder aboutVersion() {
        return this.base.aboutVersion();
    }

    /**
     * Resets all store, raster, and schema caches.
     * This operation is used to force GeoServer to drop all caches
     * and store connections and reconnect to each of them the next time
     * they are needed by a request. This is useful in case the stores themselves cache
     * some information about the data structures they manage that may have changed in the meantime.
     *
     * @return ResetCache.ResetCacheBuilder
     */
    public ResetCache.ResetCacheBuilder resetCache() {
        return this.base.resetCache();
    }

    /**
     * Reloads the GeoServer catalog and configuration from disk.
     * This operation is used in cases where an external tool has
     * modified the on-disk configuration. This operation will also
     * force GeoServer to drop any internal caches and reconnect
     * to all data stores.
     *
     * @return Reload.ReloadBuilder
     */
    public Reload.ReloadBuilder reload() {
        return this.base.reload();
    }

    /**
     * Displays a list of all fonts on the server.
     *
     * @return Fonts.FontsBuilder
     */
    public Fonts.FontsBuilder fonts() {
        return this.base.fonts();
    }

    /**
     * GET:
     * Displays a list of all logging settings on the server.
     * POST:
     * Updates logging settings on the server.
     *
     * @return Logging.LoggingBuilder
     */
    public Logging.LoggingBuilder logging() {
        return this.base.logging();
    }

    public Resource.ResourcesBuilder resources() {
        return this.base.resources();
    }

    public Workspace.WorkspacesBuilder workspaces() {
        return this.base.workspaces();
    }

    public Namespace.NamespacesBuilder namespaces() {
        return this.base.namespaces();
    }

    public GeoWebCache.GeoWebCacheBuilder gwc() {
        return this.base.gwc();
    }


    public static void main(String[] args) {
        GeoserverConnector connector = new GeoserverConnector("http://192.168.1.32:28080/geoserver/",
            "admin", "geoserver");
//        System.out.println(connector.resources().resource("workspaces").fetch());
        MapService.TMSBuilder tms =
            connector.gwc().service()
                .tms("geo-adapter-default:chengdu", "EPSG:4326", "png");
        System.out.println(tms.url());
        Object bbox = tms.fetch().get("BoundingBox");
        System.out.println(bbox);

        MapService.WMTSBuilder wmts =
            connector.gwc().service()
                .wmts("geo-adapter-default:chengdu", "EPSG:4326", "image/png");
        System.out.println(wmts.url());
        System.out.println(connector.workspaces().workspace("geo-adapter-default").coverages().fetch());
//        System.out.println(connector.aboutSystemStatus().GET());
    }
}
