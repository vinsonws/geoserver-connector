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

    public AsyncGeoserverClient(String baseurl, Authenticator authenticator) {
        this.baseurl = baseurl;
        if (authenticator == null)
            this.client = HttpClient.newBuilder().build();
        else
            this.client = HttpClient.newBuilder().authenticator(authenticator).build();
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
            .GET();
        for (Map.Entry<String, String> entry : args.getHeaders().entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }

        return client.sendAsync(builder.build(),
            HttpResponse.BodyHandlers.ofString());
    }

    private CompletableFuture<HttpResponse<String>> executeAsyncPost(AbstractCaller args, WithBody withBody) {
        HttpRequest.Builder builder = HttpRequest
            .newBuilder(URI.create(baseurl + "/" + args.getApiWithParameters()).normalize());
        builder = builder.POST(withBody.getBodyPublisher());
        for (Map.Entry<String, String> entry : args.getHeaders().entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }

        return client.sendAsync(builder.build(),
            HttpResponse.BodyHandlers.ofString());
    }

    private CompletableFuture<HttpResponse<String>> executeAsyncPut(AbstractCaller args, WithBody withBody) {
        HttpRequest.Builder builder = HttpRequest
            .newBuilder(URI.create(baseurl + "/" + args.getApiWithParameters()).normalize());
        builder = builder.PUT(withBody.getBodyPublisher());

        for (Map.Entry<String, String> entry : args.getHeaders().entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }

        return client.sendAsync(builder.build(),
            HttpResponse.BodyHandlers.ofString());
    }

    private CompletableFuture<HttpResponse<String>> executeAsyncDelete(AbstractCaller args) {
        HttpRequest.Builder builder = HttpRequest
            .newBuilder(URI.create(baseurl + "/" + args.getApiWithParameters()).normalize())
            .DELETE();
        for (Map.Entry<String, String> entry : args.getHeaders().entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }

        return client.sendAsync(builder.build(),
            HttpResponse.BodyHandlers.ofString());
    }
}
