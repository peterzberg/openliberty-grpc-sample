package dev.zberg.test.openliberty.grpc.adapter.api;

import jakarta.enterprise.context.Dependent;

@Dependent
public class InheritanceService {

    public CompositeResponse something(final A a) {
        String type = "unknown";
        A responseA = null;
        if (a.getClass().isAssignableFrom(B.class)) {
            B responseB = new B();
            responseB.setbProperty("b");
            responseB.setaProperty("b");
            responseA = responseB;
            type = "B";
        } else if (a.getClass().isAssignableFrom(C.class)) {
            C responseC = new C();
            responseC.setaProperty("c");
            responseC.setbProperty("c");
            responseC.setcProperty("c");
            responseA = responseC;
            type = "C";
        }
        final CompositeResponse response = new CompositeResponse();
        response.setType(type);
        response.setResponseA(responseA);
        return response;
    }
}
