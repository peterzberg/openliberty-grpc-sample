package dev.zberg.test.openliberty.grpc.client;

import dev.zberg.test.openliberty.grpc.api.dto.GreetingRequest;
import dev.zberg.test.openliberty.grpc.api.dto.GreetingResponse;
import dev.zberg.test.openliberty.grpc.api.HelloWorldService;
import dev.zberg.test.proto.HelloWorldServiceGrpc;
import dev.zberg.test.proto.HelloWorldServiceGrpc.HelloWorldServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class HelloWorldServiceAccessor implements HelloWorldService {

    private String host = "localhost";
    private int port = 9080;

    public void setHost(final String host) {
        this.host = host;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    @Override
    public GreetingResponse greet(GreetingRequest greetingRequest) {

        final ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        final HelloWorldServiceBlockingStub client = HelloWorldServiceGrpc.newBlockingStub(channel);

        dev.zberg.test.proto.GreetingRequest grpcRequest = dev.zberg.test.proto.GreetingRequest.newBuilder()
                .setGreeterName(greetingRequest.getGreeterName())
                .build();
        dev.zberg.test.proto.GreetingResponse grpcResponse = client.greet(grpcRequest);

        final GreetingResponse businessResponse = GreetingResponse.builder()
                .withGreeting(grpcResponse.getGreeting())
                .build();

        channel.shutdownNow();
        return businessResponse;
    }

    public static void main(String[] args) {
        final HelloWorldServiceAccessor accessor = new HelloWorldServiceAccessor();
        GreetingResponse response = accessor.greet(GreetingRequest.builder().withGreeterName("PESCHE").build());
        System.out.println(response);
    }

}
