package cn.vinsonws.tools.geoserver.connector;

import cn.vinsonws.tools.geoserver.connector.args.*;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * @author Vinsonws
 */
public class GeoserverConnector {

    private final AsyncGeoserverClient asyncClient;

    public GeoserverConnector(String baseurl, String userName, String password) {
        this.asyncClient =
            new AsyncGeoserverClient(baseurl,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password.toCharArray());
                    }
                });
    }

    /**
     * This endpoint retrieves details on all loaded JARs. All the GeoServer manifest JARs
     * are marked with the property GeoServerModule and classified by type, so you can use
     * filtering capabilities to search for a set of manifests using regular expressions
     * (see the manifest parameter) or a type category (see the key and value parameter).
     * <br>
     * The available types are core, extension, or community. To filter modules by a particular
     * type, append a request with key=GeoServerModule&value={type}
     * <br>
     * Use the “Accept:” header to specify format or append an extension to the endpoint
     * (example “/about/manifest.xml” for XML).
     * <br>
     * The model is very simple and is shared between the version and the resource requests
     * to parse both requests. You can customize the results adding a properties file called
     * manifest.properties into the root of the data directory.
     */
    public ManifestCaller.Builder aboutManifest() {
        return ManifestCaller.builder(asyncClient);
    }

    /**
     * Returns a list of system-level information. Major operating systems
     * (Linux, Windows and MacOX) are supported out of the box.
     */
    public SystemStatusCaller.Builder aboutSystemStatus() {
        return SystemStatusCaller.builder(asyncClient);
    }

    /**
     * This endpoint shows the status details of all installed and configured modules.
     * Status details always include human readable name, and module name.
     * Optional details include version, availability, status message, and links to documentation.
     */
    public StatusCaller.Builder aboutStatus() {
        return StatusCaller.builder(asyncClient);
    }

    /**
     * This endpoint shows only the details for the high-level components: GeoServer, GeoTools, and GeoWebCache.
     */
    public VersionCaller.Builder aboutVersion() {
        return VersionCaller.builder(asyncClient);
    }

    /**
     * Resets all store, raster, and schema caches.
     * This operation is used to force GeoServer to drop all caches
     * and store connections and reconnect to each of them the next time
     * they are needed by a request. This is useful in case the stores themselves cache
     * some information about the data structures they manage that may have changed in the meantime.
     */
    public ResetCacheCaller.Builder resetCache() {
        return ResetCacheCaller.builder(asyncClient);
    }

    /**
     * Reloads the GeoServer catalog and configuration from disk.
     * This operation is used in cases where an external tool has
     * modified the on-disk configuration. This operation will also
     * force GeoServer to drop any internal caches and reconnect
     * to all data stores.
     */
    public ReloadCaller.Builder reload() {
        return ReloadCaller.builder(asyncClient);
    }

    /**
     * Displays a list of all fonts on the server.
     */
    public FontsCaller.Builder fonts() {
        return FontsCaller.builder(asyncClient);
    }

    /**
     * GET:
     * Displays a list of all logging settings on the server.
     * POST:
     * Updates logging settings on the server.
     */
    public LoggingCaller.Builder logging() {
        return LoggingCaller.builder(asyncClient);
    }

    public ResourceCaller.Builder resource() {
        return ResourceCaller.builder(asyncClient);
    }

    public CoverageStoresCaller.CoverageStoresBuilder coverageStores(String workspace) {
        return CoverageStoresCaller.builder(asyncClient, workspace);
    }


    public static void main(String[] args) {
        GeoserverConnector connector = new GeoserverConnector("http://192.168.1.77:8080/geoserver/",
            "admin", "geoserver");
        System.out.println(connector.resource().resource("workspaces").GET());
//        System.out.println(connector.aboutSystemStatus().GET());
    }
}
