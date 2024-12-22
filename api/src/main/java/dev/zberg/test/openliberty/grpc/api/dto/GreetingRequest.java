package dev.zberg.test.openliberty.grpc.api.dto;

public class GreetingRequest {

    private final String greeterName;

    private GreetingRequest(Builder builder) {
        this.greeterName = builder.greeterName;
    }

    public String getGreeterName() {
        return greeterName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String greeterName;

        public Builder withGreeterName(String greeterName) {
            this.greeterName = greeterName;
            return this;
        }

        public GreetingRequest build() {
            return new GreetingRequest(this);
        }
    }

}
