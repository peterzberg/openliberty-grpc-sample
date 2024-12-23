package dev.zberg.test.openliberty.grpc.server;

import dev.zberg.test.openliberty.grpc.api.GreetingException;
import dev.zberg.test.openliberty.grpc.api.HelloWorldService;
import dev.zberg.test.openliberty.grpc.api.dto.GreetingRequest;
import dev.zberg.test.openliberty.grpc.api.dto.GreetingResponse;
import jakarta.enterprise.context.Dependent;

@Dependent
public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public GreetingResponse greet(final GreetingRequest greetingRequest) throws GreetingException {
        if ("exception".equalsIgnoreCase(greetingRequest.getGreeterName())) {
            throw new GreetingException("Cannot greet a person by name '" + greetingRequest.getGreeterName() + "'");
        }
        return GreetingResponse.builder()
                .withGreeting("Hello, %s!".formatted(greetingRequest.getGreeterName()))
                .build();
    }
}
