package cn.vinsonws.tools.geoserver.connector.args;

import cn.vinsonws.tools.geoserver.connector.AsyncGeoserverClient;

import java.util.Map;

/**
 * @author Vinsonws
 */
public final class WorkspacesCaller extends AbstractCommonCaller {
    private static final String API = "/workspaces";

    public static final class BaseWorkspacesBuilder
        extends ExecutableBuilder<BaseWorkspacesBuilder, VersionCaller>
        implements Get<VersionCaller, Map<String, Object>>, Post<VersionCaller> {

        BaseWorkspacesBuilder(AsyncGeoserverClient client) {
            super(client, API);
        }

        public WorkspacesBuilder workspace(String workspace) {
            return new WorkspacesBuilder(appendApi("/" + workspace));
        }

        public DefaultWorkspacesBuilder defaultWorkspace() {
            return new DefaultWorkspacesBuilder(appendApi("/default"));
        }

        public void create(String workspaceName) {
            this.requestBody(Map.of("name", workspaceName));
            this.POST();
        }
    }

    public static final class WorkspacesBuilder
        extends ExecutableBuilder<WorkspacesBuilder, VersionCaller>
        implements Get<VersionCaller, Map<String, Object>>,
        Put<VersionCaller>,
        Delete<VersionCaller> {
        WorkspacesBuilder(ExecutableBuilder<?, ?> other) {
            super(other);
        }

        public CoverageStoresCaller.CoverageStoresBuilder coverageStores(String name) {
            return new CoverageStoresCaller.CoverageStoresBuilder(appendApi(CoverageStoresCaller.EXTEND_API));
        }

        public void update(String workspaceName) {
            this.requestBody(Map.of("name", workspaceName));
            this.PUT();
        }
    }

    public static final class DefaultWorkspacesBuilder
        extends ExecutableBuilder<DefaultWorkspacesBuilder, VersionCaller>
        implements Get<VersionCaller, Map<String, Object>>,
        Put<VersionCaller> {
        DefaultWorkspacesBuilder(ExecutableBuilder<?, ?> other) {
            super(other);
        }

        public void update(String existWorkspaceName) {
            this.requestBody(Map.of("workspace", Map.of("name", existWorkspaceName)));
            this.PUT();
        }
    }
}
