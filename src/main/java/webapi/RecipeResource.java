package webapi;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api/recipes")
public class RecipeResource
{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response test()
    {
        return Response
            .status(Response.Status.OK)
            //.entity()
            .build();
    }
}
