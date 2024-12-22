package dev.zberg.test.openliberty.grpc.server;

import dev.zberg.test.openliberty.grpc.api.dto.GreetingRequest;
import dev.zberg.test.openliberty.grpc.api.dto.GreetingResponse;
import dev.zberg.test.openliberty.grpc.api.HelloWorldService;
import jakarta.enterprise.context.Dependent;

@Dependent
public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public GreetingResponse greet(final GreetingRequest greetingRequest) {
        return GreetingResponse.builder()
                .withGreeting("Hello, %s!".formatted(greetingRequest.getGreeterName()))
                .build();
    }
}
