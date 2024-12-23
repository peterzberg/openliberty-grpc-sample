package dev.zberg.test.openliberty.grpc.api;

import dev.zberg.test.openliberty.grpc.api.dto.GreetingRequest;
import dev.zberg.test.openliberty.grpc.api.dto.GreetingResponse;

public interface HelloWorldService {

    GreetingResponse greet(GreetingRequest greetingRequest) throws GreetingException;
}
