package cn.vinsonws.tools.geoserver.connector;

import cn.vinsonws.tools.geoserver.connector.caller.*;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * @author Vinsonws
 */
public class GeoserverConnector {

    private final AsyncGeoserverClient asyncClient;
    private final Base.BaseBuilder base;

    public GeoserverConnector(String baseurl, String userName, String password) {
        this.asyncClient =
            new AsyncGeoserverClient(baseurl,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password.toCharArray());
                    }
                });
        this.base = Base.base(this.asyncClient, "/rest");
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
    public Manifest.ManifestBuilder aboutManifest() {
        return this.base.aboutManifest();
    }

    /**
     * Returns a list of system-level information. Major operating systems
     * (Linux, Windows and MacOX) are supported out of the box.
     */
    public SystemStatus.SystemStatusBuilder aboutSystemStatus() {
        return this.base.aboutSystemStatus();
    }

    /**
     * This endpoint shows the status details of all installed and configured modules.
     * Status details always include human readable name, and module name.
     * Optional details include version, availability, status message, and links to documentation.
     */
    public Status.Builder aboutStatus() {
        return this.base.aboutStatus();
    }

    /**
     * This endpoint shows only the details for the high-level components: GeoServer, GeoTools, and GeoWebCache.
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
     */
    public Reload.ReloadBuilder reload() {
        return this.base.reload();
    }

    /**
     * Displays a list of all fonts on the server.
     */
    public Fonts.FontsBuilder fonts() {
        return this.base.fonts();
    }

    /**
     * GET:
     * Displays a list of all logging settings on the server.
     * POST:
     * Updates logging settings on the server.
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


    public static void main(String[] args) {
        GeoserverConnector connector = new GeoserverConnector("http://192.168.1.77:8080/geoserver/",
            "admin", "geoserver");
        System.out.println(connector.resources().resource("workspaces").fetch());
//        System.out.println(connector.aboutSystemStatus().GET());
    }
}
