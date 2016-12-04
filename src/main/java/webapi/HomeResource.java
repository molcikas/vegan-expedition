package webapi;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("")
public class HomeResource
{
    @GET
    public Response testEndpoint()
    {
        return Response
            .status(Response.Status.OK)
            .entity("Test response.")
            .build();
    }
}
