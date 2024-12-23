package dev.zberg.test.openliberty.grpc.client;

import dev.zberg.test.openliberty.grpc.api.GreetingException;
import dev.zberg.test.openliberty.grpc.api.HelloWorldService;
import dev.zberg.test.openliberty.grpc.api.dto.GreetingRequest;
import dev.zberg.test.openliberty.grpc.api.dto.GreetingResponse;
import dev.zberg.test.proto.HelloWorldServiceGrpc;
import dev.zberg.test.proto.HelloWorldServiceGrpc.HelloWorldServiceBlockingStub;
import io.grpc.*;

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
    public GreetingResponse greet(GreetingRequest greetingRequest) throws GreetingException {

        try {
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
        } catch (StatusRuntimeException e) {
            final Metadata metadata = e.getTrailers();
            final Metadata.Key<String> errorClassKey = Metadata.Key.of("error-class", Metadata.ASCII_STRING_MARSHALLER);
            if (metadata != null && metadata.containsKey(errorClassKey)) {
                final String errorClass = metadata.get(errorClassKey);
                if (GreetingException.class.getName().equals(errorClass)) {
                    final String message = e.getStatus().getDescription();
                    throw new GreetingException(message);
                }
            }
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static void main(String[] args) throws GreetingException {
        final HelloWorldServiceAccessor accessor = new HelloWorldServiceAccessor();
        GreetingResponse response = accessor.greet(GreetingRequest.builder().withGreeterName("PESCHE").build());
        System.out.println(response);
    }

}
