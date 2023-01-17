package cn.vinsonws.tools.geoserver.connector.args;

/**
 * @author Vinsonws
 */
public class GetManifestArgs extends AbstractCommonArgs {
    static final String API = "/rest/about/manifest.json";

    public GetManifestArgs(String api) {
        super(api);
    }

    public static Builder builder() {
        return new Builder(API);
    }

    public static final class Builder extends AbstractArgs.Builder<Builder, GetManifestArgs> {
        private Builder(String api) {
            super(api);
        }
    }
}
