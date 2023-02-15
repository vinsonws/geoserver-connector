package cn.vinsonws.tools.geoserver.connector;

import cn.vinsonws.tools.geoserver.connector.body.WithBodies;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

/**
 * @author Vinsonws
 */
@Ignore
public class GeoserverConnectorTest {
    static GeoserverConnector connector;

    @BeforeClass
    public static void initConnector() {
        connector = new GeoserverConnector("http://192.168.1.32:28080/geoserver/",
            "admin", "geoserver");
    }


    @Test
    public void aboutManifest() {
        System.out.println(connector.aboutManifest().fetch());
    }

    @Test
    public void aboutSystemStatus() {
        System.out.println(connector.aboutSystemStatus().fetch());
    }

    @Test
    public void namespace() {
        connector.namespaces().create("zws", "http://localhost/zws");
        System.out.println(connector.namespaces().namespace("zws"));
    }

    @Test
    public void workspace() {
        connector.workspaces().create("zwsws");
        System.out.println(connector.workspaces().fetch());
    }

    @Test
    public void coverageStore() {
        System.out.println(connector.workspaces().workspace("zwsws").coverageStores().fetch());
//        connector.workspaces().workspace("zwsws").coverageStores().create(
//            WithBodies.JSON(Map.of("coverageStore", Map.of(
//                "name", "dom5",
//                "enabled", "true",
//                "workspace", "zwsws",
//                "type", "GeoTIFF",
//                "url", "file:///opt/gc/DOM_5m.tif"
//            )))
//        );
        System.out.println(connector.workspaces().workspace("zwsws").coverageStores().store("dom5").fetch());
    }

        @Test
    public void layer() {
        System.out.println(connector.gwc()
            .layers().layer("geo-adapter-default:zws111")
                .fetch());
    }
}
