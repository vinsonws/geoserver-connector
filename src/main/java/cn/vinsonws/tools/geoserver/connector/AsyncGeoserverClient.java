package cn.vinsonws.tools.geoserver.connector;

import cn.vinsonws.tools.geoserver.connector.args.AbstractCaller;
import cn.vinsonws.tools.geoserver.connector.args.WithBody;

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

    public CompletableFuture<HttpResponse<String>> executeAsync(AbstractCaller args) {
        return switch (args.getMethod()) {
            case GET -> executeAsyncGet(args);
            case POST -> executeAsyncPost(args);
            case PUT -> executeAsyncPut(args);
            case DELETE -> executeAsyncDelete(args);
            default -> throw new UnsupportedOperationException(args.getMethod() + " method not supported");
        };
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

    private CompletableFuture<HttpResponse<String>> executeAsyncPost(AbstractCaller args) {
        HttpRequest.Builder builder = HttpRequest
            .newBuilder(URI.create(baseurl + "/" + args.getApiWithParameters()).normalize());
        if (args instanceof WithBody withBody) {
            builder = builder.POST(withBody.getBodyPublisher());
        } else {
            throw new IllegalArgumentException(args.getMethod() + " must implement WithBody");
        }
        for (Map.Entry<String, String> entry : args.getHeaders().entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }

        return client.sendAsync(builder.build(),
            HttpResponse.BodyHandlers.ofString());
    }

    private CompletableFuture<HttpResponse<String>> executeAsyncPut(AbstractCaller args) {
        HttpRequest.Builder builder = HttpRequest
            .newBuilder(URI.create(baseurl + "/" + args.getApiWithParameters()).normalize());
        if (args instanceof WithBody withBody) {
            builder = builder.PUT(withBody.getBodyPublisher());
        } else {
            throw new IllegalArgumentException(args.getMethod() + " must implement WithBody");
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
            .DELETE();
        for (Map.Entry<String, String> entry : args.getHeaders().entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }

        return client.sendAsync(builder.build(),
            HttpResponse.BodyHandlers.ofString());
    }
}
