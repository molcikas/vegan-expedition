package webapi;

import aggregates.recipe.Recipe;
import applicationservices.RecipeAppService;
import bootstrapping.MainRunner;
import org.apache.tapestry5.ioc.annotations.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("api/recipes")
public class RecipeResource
{

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll()
    {
        // TODO: Figure out how to integrate tapestry IOC with jersey so we don't have to do this.
        RecipeAppService recipeAppService = MainRunner.getRegistry().getService(RecipeAppService.class);

        List<Recipe> recipes = recipeAppService.getAll();

        return Response
            .status(Response.Status.OK)
            .entity(recipes)
            .build();
    }
}
