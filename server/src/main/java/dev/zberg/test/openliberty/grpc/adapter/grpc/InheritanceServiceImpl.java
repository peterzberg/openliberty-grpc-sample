package dev.zberg.test.openliberty.grpc.adapter.grpc;

import dev.zberg.test.openliberty.grpc.InheritanceServiceGrpc;
import dev.zberg.test.openliberty.grpc.RequestWithInheritance;
import dev.zberg.test.openliberty.grpc.ResponseWithInheritance;
import dev.zberg.test.openliberty.grpc.adapter.api.A;
import dev.zberg.test.openliberty.grpc.adapter.api.InheritanceService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Inject;

public class InheritanceServiceImpl extends InheritanceServiceGrpc.InheritanceServiceImplBase {

    @Inject
    private InheritanceService inheritanceService;

    @Override
    public void inheritanceTest(final RequestWithInheritance request, final StreamObserver<ResponseWithInheritance> responseObserver) {
        try {
            final InheritanceMapper inheritanceMapper = new InheritanceMapper();
            final A a = inheritanceMapper.map(request);
            dev.zberg.test.openliberty.grpc.adapter.api.CompositeResponse compositeResponse = inheritanceService.something(a);
            responseObserver.onNext(inheritanceMapper.map(compositeResponse));
            responseObserver.onCompleted();
        } catch (RuntimeException e) {
            java.util.logging.Logger.getLogger(InheritanceServiceImpl.class.getName()).log(java.util.logging.Level.SEVERE, e.getMessage(), e);
            responseObserver.onError(Status.INTERNAL.withCause(e).asException());
        }
    }
}
