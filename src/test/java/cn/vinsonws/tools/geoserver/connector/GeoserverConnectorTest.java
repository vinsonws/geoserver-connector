package cn.vinsonws.tools.geoserver.connector;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Vinsonws
 */
@Ignore
public class GeoserverConnectorTest {
    static GeoserverConnector connector;

    @BeforeClass
    public static void initConnector() {
        connector = new GeoserverConnector("http://192.168.1.77:8080/geoserver/",
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
}
