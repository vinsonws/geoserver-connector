package cn.vinsonws.tools.geoserver.connector.util;

import java.net.http.HttpResponse;

/**
 * @author Vinsonws
 */
public class HttpUtils {
    public static boolean validateResponse(HttpResponse<?> response) {
        return response.statusCode() >= 200 && response.statusCode() <= 206;
    }
}
