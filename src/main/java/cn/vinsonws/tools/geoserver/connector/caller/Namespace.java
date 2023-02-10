package cn.vinsonws.tools.geoserver.connector.caller;

import cn.vinsonws.tools.geoserver.connector.body.WithBodies;
import cn.vinsonws.tools.geoserver.connector.util.Verification;

import java.util.Map;

public final class Namespace {
    public static final class NamespacesBuilder
        extends AbstractCaller.ExecutableBuilder<NamespacesBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Post {
        private static final String EXTEND_API = "/namespaces";

        NamespacesBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }

        public NamespaceBuilder namespace(String namespaceName) {
            return new NamespaceBuilder(this, Verification.validateNotEmptyString("namespaceName", namespaceName));
        }


        public void create(String prefix, String uri) {
            this.create(WithBodies.JSON(Map.of("namespace", Map.of(
                "prefix", Verification.validateNotEmptyString("prefix", prefix),
                "uri", Verification.validateNotEmptyString("uri", uri)))));
        }
    }

    public static final class NamespaceBuilder
        extends AbstractCaller.ExecutableBuilder<NamespaceBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Put,
        AbstractCaller.Delete {
        NamespaceBuilder(AbstractCaller.ExecutableBuilder<?> other, String namespaceName) {
            super(other);
            appendApi("/" + namespaceName);
        }
    }
}
