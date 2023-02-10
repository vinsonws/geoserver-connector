package cn.vinsonws.tools.geoserver.connector.exception;

/**
 * @author Vinsonws
 */
public class GeoserverServiceFailedRuntimeException extends RuntimeException {
    private final int statusCode;

    public GeoserverServiceFailedRuntimeException(int statusCode, String url, String message) {
        super("geoserver http status code cannot be " + statusCode + "(" + url + "): " + message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
