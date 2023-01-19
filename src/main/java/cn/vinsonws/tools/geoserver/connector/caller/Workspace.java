package cn.vinsonws.tools.geoserver.connector.caller;

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
            return new WorkspaceBuilder(this, workspace);
        }

        public DefaultWorkspaceBuilder defaultWorkspace() {
            return new DefaultWorkspaceBuilder(appendApi("/default"));
        }

        public void create(String workspaceName) {
            this.requestBody(Map.of("name", workspaceName));
            this.POST();
        }

        @Override
        public WithBody withPostBody() {
            return WithBodies.JSON(requestBody);
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
            this.requestBody(Map.of("name", workspaceName));
            this.PUT();
        }

        @Override
        public WithBody withPutBody() {
            return WithBodies.JSON(requestBody);
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
            this.requestBody(Map.of("workspace", Map.of("name", existWorkspaceName)));
            this.PUT();
        }

        @Override
        public WithBody withPutBody() {
            return WithBodies.JSON(requestBody);
        }
    }
}
