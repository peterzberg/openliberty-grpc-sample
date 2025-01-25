package dev.zberg.test.openliberty.grpc.adapter.grpc;

import dev.zberg.test.openliberty.grpc.GreetingRequest;
import dev.zberg.test.openliberty.grpc.GreetingResponse;
import io.grpc.stub.StreamObserver;

public class HelloWorldServiceGrpcImpl extends dev.zberg.test.openliberty.grpc.HelloWorldServiceGrpc.HelloWorldServiceImplBase {

    @Override
    public void greet(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        responseObserver.onNext(GreetingResponse.newBuilder().setGreeting(String.format("Hello, %s!", request.getGreeterName())).build());
        responseObserver.onCompleted();
    }
}
