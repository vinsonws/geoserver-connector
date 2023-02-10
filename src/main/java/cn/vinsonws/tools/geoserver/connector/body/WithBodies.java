package cn.vinsonws.tools.geoserver.connector.body;

/**
 * @author Vinsonws
 */
public final class WithBodies {
    public static WithBody JSON(Object o) {
        return new WithBody.JsonBody(o);
    }

    public final static WithBody EMPTY = new WithBody.Empty() {
        @Override
        public void validate() {
        }

        @Override
        public String getContentType() {
            return null;
        }
    };
}
