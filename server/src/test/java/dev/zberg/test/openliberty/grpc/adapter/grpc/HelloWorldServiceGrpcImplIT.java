package dev.zberg.test.openliberty.grpc.adapter.grpc;

import dev.zberg.test.openliberty.grpc.GreetingRequest;
import dev.zberg.test.openliberty.grpc.GreetingResponse;
import dev.zberg.test.openliberty.grpc.HelloWorldServiceGrpc;
import dev.zberg.test.openliberty.grpc.helper.ChannelFactory.ChannelConfig;
import dev.zberg.test.openliberty.grpc.helper.ChannelFactoryProducer;
import io.grpc.ManagedChannel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class HelloWorldServiceGrpcImplIT {

    private ManagedChannel channel;

    @BeforeEach
    void setUp() {
        channel = ChannelFactoryProducer.getChannelFactory().getOrCreateChannel(new ChannelConfig("localhost", 9080, true));
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        channel.shutdownNow();
        channel.awaitTermination(3L, TimeUnit.SECONDS);
    }

    @Test
    void testGreeting() {
        final HelloWorldServiceGrpc.HelloWorldServiceBlockingStub grpcService = HelloWorldServiceGrpc.newBlockingStub(channel);

        final GreetingResponse greetingResponse = grpcService.greet(GreetingRequest.newBuilder().setGreeterName("World").build());

        assertThat(greetingResponse).isNotNull();
        assertThat(greetingResponse.getGreeting()).isEqualTo("Hello, World!");
    }

}