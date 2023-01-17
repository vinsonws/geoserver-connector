package cn.vinsonws.tools.geoserver.connector.args;

import java.net.http.HttpRequest;

/**
 * @author Vinsonws
 */
public abstract class AbstractCommonArgs extends AbstractArgs {
    protected AbstractCommonArgs(String api) {
        super(api);
    }

    @Override
    public HttpRequest.BodyPublisher getRequestBody() {
        throw new UnsupportedOperationException("Submission of request body is not supported.");
    }
}
