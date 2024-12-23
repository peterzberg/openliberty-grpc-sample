package dev.zberg.test.openliberty.grpc.server.rest;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

class GreetingResourceIT {

    @Test
    void testGreeting() throws IOException, InterruptedException {
        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest greetingRequest = HttpRequest.newBuilder(URI.create("http://localhost:9080/api/greeting/grpc"))
                .GET()
                .header("accept", "text/plain")
                .build();

        final HttpResponse<String> response = client.send(greetingRequest, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("Hello, grpc!");
    }

    @Test
    void testErrorHandling() throws IOException, InterruptedException {
        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest greetingRequest = HttpRequest.newBuilder(URI.create("http://localhost:9080/api/greeting/runtimeException"))
                .GET()
                .header("accept", "text/plain")
                .build();

        final HttpResponse<String> response = client.send(greetingRequest, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(500);
        assertThat(response.body()).isEqualTo("INTERNAL: runtimeException");
    }

    @Test
    void testKnownException() throws IOException, InterruptedException {
        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest greetingRequest = HttpRequest.newBuilder(URI.create("http://localhost:9080/api/greeting/exception"))
                .GET()
                .header("accept", "text/plain")
                .build();

        final HttpResponse<String> response = client.send(greetingRequest, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.body()).isEqualTo("Cannot greet a person by name 'exception'");
    }

}