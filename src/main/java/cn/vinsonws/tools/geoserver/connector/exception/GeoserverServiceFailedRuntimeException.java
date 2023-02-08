package cn.vinsonws.tools.geoserver.connector.exception;

/**
 * @author Vinsonws
 */
public class GeoserverServiceFailedRuntimeException extends RuntimeException {
    private final int statusCode;

    public GeoserverServiceFailedRuntimeException(int statusCode, String url) {
        super("geoserver http status code cannot be " + statusCode + "(" + url + ")");
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
