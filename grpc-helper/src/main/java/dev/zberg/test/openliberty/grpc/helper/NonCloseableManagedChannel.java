package dev.zberg.test.openliberty.grpc.helper;

import io.grpc.CallOptions;
import io.grpc.ClientCall;
import io.grpc.ManagedChannel;
import io.grpc.MethodDescriptor;

import java.util.concurrent.TimeUnit;

class NonCloseableManagedChannel extends ManagedChannel {

    private final ManagedChannel delegate;
    private boolean shutdown = false;

    NonCloseableManagedChannel(ManagedChannel delegate) {
        this.delegate = delegate;
    }

    ManagedChannel getDelegate() {
        return delegate;
    }

    @Override
    public ManagedChannel shutdown() {
        // do not really shutdown managed channels
        shutdown = true;
        return this;
    }

    @Override
    public boolean isShutdown() {
        return shutdown;
    }

    @Override
    public boolean isTerminated() {
        return shutdown;
    }

    @Override
    public ManagedChannel shutdownNow() {
        return this;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return shutdown;
    }

    @Override
    public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(MethodDescriptor<RequestT, ResponseT> methodDescriptor, CallOptions callOptions) {
        return delegate.newCall(methodDescriptor, callOptions);
    }

    @Override
    public String authority() {
        return delegate.authority();
    }
}
