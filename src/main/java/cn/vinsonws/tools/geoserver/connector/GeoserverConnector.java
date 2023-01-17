package cn.vinsonws.tools.geoserver.connector;

import cn.vinsonws.tools.geoserver.connector.args.GetManifestArgs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author Vinsonws
 */
public class GeoserverConnector {

    private final AsyncGeoserverConnector asyncConnector;

    public GeoserverConnector(String baseurl, String userName, String password) {
        this.asyncConnector =
            new AsyncGeoserverConnector(baseurl,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password.toCharArray());
                    }
                });
    }

    public Map<String, Object> manifest() {
        try {
            return asyncConnector.executeAsyncGet(GetManifestArgs.builder().build())
                .thenApply(response -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        // convert JSON string to Map
                        return mapper.readValue(response.body(), new TypeReference<Map<String, Object>>() {
                        });
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        GeoserverConnector connector = new GeoserverConnector("http://192.168.1.77:8080/geoserver/",
            "admin", "geoserver");
        System.out.println(connector.manifest());
    }
}
