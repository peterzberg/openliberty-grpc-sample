package dev.zberg.test.openliberty.grpc.api;

public class GreetingException extends Exception {
    public GreetingException(String message) {
        super(message);
    }
}
