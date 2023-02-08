package cn.vinsonws.tools.geoserver.connector.caller;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class Manifest {

    public static final class ManifestBuilder extends AbstractCaller.ExecutableBuilder<ManifestBuilder>
        implements AbstractCaller.Get<Map<String, Object>> {
        private static final String EXTEND_API = "/about/manifest.json";

        ManifestBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }
    }
}
