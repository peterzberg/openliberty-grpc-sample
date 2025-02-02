package dev.zberg.test.openliberty.grpc.adapter.api;

public class CompositeResponse {
    private String type;
    private A responseA;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public A getResponseA() {
        return responseA;
    }

    public void setResponseA(A responseA) {
        this.responseA = responseA;
    }
}
