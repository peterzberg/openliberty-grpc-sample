package dev.zberg.test.openliberty.grpc.adapter.rest;

import dev.zberg.test.openliberty.grpc.*;
import dev.zberg.test.openliberty.grpc.helper.ChannelFactory;
import dev.zberg.test.openliberty.grpc.helper.ChannelFactoryProducer;
import io.grpc.ManagedChannel;
import jakarta.ws.rs.*;

@Path("/inheritance")
public class InheritanceRessource {

    @GET
    @Produces({"text/plain"})
    public String something(@QueryParam("type") final String type) {
        final ChannelFactory channelFactory = ChannelFactoryProducer.getChannelFactory();
        final ManagedChannel channel = channelFactory.getOrCreateChannel(new ChannelFactory.ChannelConfig("localhost", 9080, true));
        final InheritanceServiceGrpc.InheritanceServiceBlockingStub stub = InheritanceServiceGrpc.newBlockingStub(channel);
        try {
            final ResponseWithInheritance result = stub.inheritanceTest(createRequestByType(type));
            return result.getA().toString();
        } finally {
            channel.shutdown();
        }
    }

    private RequestWithInheritance createRequestByType(String type) {
        if (type == null) {
            throw new BadRequestException("unknown type: " + type);
        }
        final AInheritance.Builder aBuilder = AInheritance.newBuilder();
        if (type.equalsIgnoreCase("b")) {
            final A a = A.newBuilder().setAProperty("aProperty").build();
            final B b = B.newBuilder().setBProperty("bProperty")
                    .setSuperType(a)
                    .build();
            aBuilder.setB(b).build();
        } else if (type.equalsIgnoreCase("c")) {
            final A a = A.newBuilder().setAProperty("aProperty").build();
            final B b = B.newBuilder().setBProperty("bProperty")
                    .setSuperType(a)
                    .build();
            final C c = C.newBuilder().setCProperty("cProperty")
                    .setSuperType(b)
                    .build();
            aBuilder.setC(c).build();
        } else {
            throw new BadRequestException("unknown type: " + type);
        }
        return RequestWithInheritance.newBuilder().setA(aBuilder.build()).build();
    }
}
