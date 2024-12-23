package dev.zberg.test.openliberty.grpc.server.rest;

import dev.zberg.test.openliberty.grpc.api.GreetingException;
import dev.zberg.test.openliberty.grpc.api.dto.GreetingRequest;
import dev.zberg.test.openliberty.grpc.api.dto.GreetingResponse;
import dev.zberg.test.openliberty.grpc.client.HelloWorldServiceAccessor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/greeting")
@ApplicationScoped
public class GreetingResource {

    @GET
    @Produces("text/plain")
    @Path("{name}")
    public String getGreeting(@PathParam("name") String name) {
        try {
            final HelloWorldServiceAccessor accessor = new HelloWorldServiceAccessor();
            accessor.setHost("localhost");
            accessor.setPort(9080);
            final GreetingResponse response = accessor.greet(GreetingRequest.builder().withGreeterName(name).build());
            return response.getGreeting();
        } catch (GreetingException e) {
            throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
        }
    }
}
