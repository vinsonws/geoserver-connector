package cn.vinsonws.tools.geoserver.connector;

import cn.vinsonws.tools.geoserver.connector.args.AbstractCommonArgs;
import cn.vinsonws.tools.geoserver.connector.args.GetManifestArgs;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Vinsonws
 */
public class AsyncGeoserverConnector {
    private final String baseurl;
    private final HttpClient client;

    public AsyncGeoserverConnector(String baseurl, Authenticator authenticator) {
        this.baseurl = baseurl;
        if (authenticator == null)
            this.client = HttpClient.newBuilder().build();
        else
            this.client = HttpClient.newBuilder().authenticator(authenticator).build();
    }

    protected CompletableFuture<HttpResponse<String>> executeAsyncGet(AbstractCommonArgs args) {
        HttpRequest.Builder builder = HttpRequest
            .newBuilder(URI.create(baseurl + "/" + args.getApi()).normalize())
            .GET();
        for (Map.Entry<String, String> entry : args.getHeaders().entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }

        return client.sendAsync(builder
                .GET()
                .build(),
            HttpResponse.BodyHandlers.ofString());
    }

//    protected CompletableFuture<HttpResponse<String>> executeAsyncGet(String api, ) {
//        return client.sendAsync(HttpRequest
//                .newBuilder(URI.create(baseurl + "/" + api).normalize())
//                .POST()
//                .build(),
//            HttpResponse.BodyHandlers.ofString());
//    }

    public static void main(String[] args) throws URISyntaxException, ExecutionException, InterruptedException, MalformedURLException {
        AsyncGeoserverConnector connector =
            new AsyncGeoserverConnector("http://192.168.1.77:8080/geoserver/",
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("admin", "geoserver".toCharArray());
                    }
                });
        connector.executeAsyncGet(GetManifestArgs.builder().build())//.thenApply(HttpResponse::body)
            .thenAccept(System.out::println).get();
    }
}
