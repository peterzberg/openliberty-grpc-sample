package dev.zberg.test.openliberty.grpc.api.dto;

public class GreetingResponse {
    private final String greeting;

    private GreetingResponse(Builder builder) {
        this.greeting = builder.greeting;
    }

    public String getGreeting() {
        return greeting;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String greeting;

        public Builder withGreeting(String greeting) {
            this.greeting = greeting;
            return this;
        }

        public GreetingResponse build() {
            return new GreetingResponse(this);
        }
    }
}
