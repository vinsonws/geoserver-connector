package cn.vinsonws.tools.geoserver.connector.exception;

/**
 * @author Vinsonws
 */
public class GeoserverServiceFailedRuntimeException extends RuntimeException {
    private final int statusCode;

    public GeoserverServiceFailedRuntimeException(int statusCode) {
        super("geoserver http status code cannot be " + statusCode);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
