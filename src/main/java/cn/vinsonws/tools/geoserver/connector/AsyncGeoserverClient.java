package cn.vinsonws.tools.geoserver.connector;

import cn.vinsonws.tools.geoserver.connector.caller.AbstractCaller;
import cn.vinsonws.tools.geoserver.connector.body.WithBody;

import java.net.Authenticator;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Vinsonws
 */
public class AsyncGeoserverClient {
    private final String baseurl;
    private final HttpClient client;

    private final String authHeader;

    public AsyncGeoserverClient(String baseurl, String authHeader) {
        this.baseurl = baseurl;
        this.client = HttpClient.newBuilder().build();
        this.authHeader = authHeader;
    }

    public CompletableFuture<HttpResponse<String>> executeAsync(AbstractCaller args, WithBody withBody) {
        CompletableFuture<HttpResponse<String>> resp;
        switch (args.getMethod()) {
            case GET:
                resp = executeAsyncGet(args);
                break;
            case POST:
                resp = executeAsyncPost(args, withBody);
                break;
            case PUT:
                resp = executeAsyncPut(args, withBody);
                break;
            case DELETE:
                resp = executeAsyncDelete(args);
                break;
            default:
                throw new UnsupportedOperationException(args.getMethod() + " method not supported");
        }
        return resp;
    }

    private CompletableFuture<HttpResponse<String>> executeAsyncGet(AbstractCaller args) {
        HttpRequest.Builder builder = HttpRequest
            .newBuilder(URI.create(baseurl + "/" + args.getApiWithParameters()).normalize())
            .header("Authorization", authHeader)
            .header("Accept", "application/json")
            .GET();
        for (Map.Entry<String, String> entry : args.getHeaders().entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }

        return client.sendAsync(builder.build(),
            HttpResponse.BodyHandlers.ofString());
    }

    private CompletableFuture<HttpResponse<String>> executeAsyncPost(AbstractCaller args, WithBody withBody) {
        HttpRequest.Builder builder = HttpRequest
            .newBuilder(URI.create(baseurl + "/" + args.getApiWithParameters()).normalize())
            .header("Authorization", authHeader)
            .header("Accept", "application/json")
            .POST(withBody.getBodyPublisher());
        if (withBody.getContentType() != null) {
            builder = builder.header("Content-Type", withBody.getContentType());
        }
        for (Map.Entry<String, String> entry : args.getHeaders().entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }

        return client.sendAsync(builder.build(),
            HttpResponse.BodyHandlers.ofString());
    }

    private CompletableFuture<HttpResponse<String>> executeAsyncPut(AbstractCaller args, WithBody withBody) {
        HttpRequest.Builder builder = HttpRequest
            .newBuilder(URI.create(baseurl + "/" + args.getApiWithParameters()).normalize());
        builder = builder
            .header("Authorization", authHeader)
            .header("Accept", "application/json")
            .PUT(withBody.getBodyPublisher());
        if (withBody.getContentType() != null) {
            builder = builder.header("Content-Type", withBody.getContentType());
        }
        for (Map.Entry<String, String> entry : args.getHeaders().entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }

        return client.sendAsync(builder.build(),
            HttpResponse.BodyHandlers.ofString());
    }

    private CompletableFuture<HttpResponse<String>> executeAsyncDelete(AbstractCaller args) {
        HttpRequest.Builder builder = HttpRequest
            .newBuilder(URI.create(baseurl + "/" + args.getApiWithParameters()).normalize())
            .header("Authorization", authHeader)
            .header("Accept", "application/json")
            .DELETE();
        for (Map.Entry<String, String> entry : args.getHeaders().entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }

        return client.sendAsync(builder.build(),
            HttpResponse.BodyHandlers.ofString());
    }
}
