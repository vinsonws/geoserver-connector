package cn.vinsonws.tools.geoserver.connector.caller;

/**
 * @author Vinsonws
 */
public final class WithBodies {
    public static WithBody JSON(Object o) {
        return new WithBody.Json() {
            @Override
            public Object getRequestBody() {
                return o;
            }
        };
    }

    public final static WithBody EMPTY = new WithBody.Empty() {};
}
