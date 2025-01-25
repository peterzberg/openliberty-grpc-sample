package dev.zberg.test.openliberty.grpc.adapter.rest;

import dev.zberg.test.openliberty.grpc.GreetingRequest;
import dev.zberg.test.openliberty.grpc.GreetingResponse;
import dev.zberg.test.openliberty.grpc.HelloWorldServiceGrpc;
import dev.zberg.test.openliberty.grpc.helper.ChannelFactory;
import dev.zberg.test.openliberty.grpc.helper.ChannelFactory.ChannelConfig;
import dev.zberg.test.openliberty.grpc.helper.ChannelFactoryProducer;
import io.grpc.ManagedChannel;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

@Path("/hello")
public class HelloWorldResource {

    @GET
    @Produces({"text/plain"})
    public String hello(@QueryParam("name") final String name) {
        final ChannelFactory channelFactory = ChannelFactoryProducer.getChannelFactory();
        final ManagedChannel channel = channelFactory.getOrCreateChannel(new ChannelConfig("localhost", 9080, true));
        final HelloWorldServiceGrpc.HelloWorldServiceBlockingStub stub = HelloWorldServiceGrpc.newBlockingStub(channel);
        final String greeterName = name != null ? name : "World";
        try {
            final GreetingResponse result = stub.greet(GreetingRequest.newBuilder().setGreeterName(greeterName).build());
            return result.getGreeting();
        } finally {
            channel.shutdown();
        }
    }
}
