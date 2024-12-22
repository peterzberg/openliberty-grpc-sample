package dev.zberg.test.openliberty.grpc.server;

import dev.zberg.test.openliberty.grpc.api.HelloWorldService;
import dev.zberg.test.proto.GreetingRequest;
import dev.zberg.test.proto.GreetingResponse;
import dev.zberg.test.proto.HelloWorldServiceGrpc;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Inject;

public class HelloWorldServiceGrpcImpl extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {

    @Inject
    private HelloWorldService delegate;

    @Override
    public void greet(final GreetingRequest request, final StreamObserver<GreetingResponse> responseObserver) {

        try {

            final dev.zberg.test.openliberty.grpc.api.dto.GreetingRequest mappedRequest = dev.zberg.test.openliberty.grpc.api.dto.GreetingRequest.builder()
                    .withGreeterName(request.getGreeterName())
                    .build();

            dev.zberg.test.openliberty.grpc.api.dto.GreetingResponse response = delegate.greet(mappedRequest);

            final GreetingResponse mappedResponse = GreetingResponse.newBuilder()
                    .setGreeting(response.getGreeting())
                    .build();

            responseObserver.onNext(mappedResponse);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
