package cn.vinsonws.tools.geoserver.connector.caller;

import cn.vinsonws.tools.geoserver.connector.body.WithBodies;
import cn.vinsonws.tools.geoserver.connector.util.Verification;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class Workspace {

    public static final class WorkspacesBuilder
        extends AbstractCaller.ExecutableBuilder<WorkspacesBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Post {
        private static final String EXTEND_API = "/workspaces";

        WorkspacesBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }

        public WorkspaceBuilder workspace(String workspace) {
            return new WorkspaceBuilder(this, Verification.validateNotEmptyString("workspace", workspace));
        }

        public DefaultWorkspaceBuilder defaultWorkspace() {
            return new DefaultWorkspaceBuilder(appendApi("/default"));
        }

        public void create(String workspaceName) {
            this.create(WithBodies.JSON(Map.of("workspace",
                Map.of("name", Verification.validateNotEmptyString("workspaceName", workspaceName)))));
        }
    }

    public static final class WorkspaceBuilder
        extends AbstractCaller.ExecutableBuilder<WorkspaceBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Put,
        AbstractCaller.Delete {
        WorkspaceBuilder(AbstractCaller.ExecutableBuilder<?> other, String workspace) {
            super(other);
            appendApi("/" + workspace);
        }

        public CoverageStore.CoverageStoresBuilder coverageStores() {
            return new CoverageStore.CoverageStoresBuilder(this);
        }

        public void update(String workspaceName) {
            this.update(WithBodies.JSON(Map.of("name", Verification.validateNotEmptyString("workspaceName", workspaceName))));
        }
    }

    public static final class DefaultWorkspaceBuilder
        extends AbstractCaller.ExecutableBuilder<DefaultWorkspaceBuilder>
        implements AbstractCaller.Get<Map<String, Object>>,
        AbstractCaller.Put {
        private static final String EXTEND_API = "/default";

        DefaultWorkspaceBuilder(AbstractCaller.ExecutableBuilder<?> other) {
            super(other);
            appendApi(EXTEND_API);
        }

        public void update(String existWorkspaceName) {
            this.update(WithBodies.JSON(Map.of("workspace", Map.of("name",
                Verification.validateNotEmptyString("existWorkspaceName", existWorkspaceName)))));
        }
    }
}
