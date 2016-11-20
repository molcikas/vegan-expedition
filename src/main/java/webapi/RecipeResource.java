package webapi;

import aggregates.recipe.Recipe;
import applicationservices.RecipeAppService;
import bootstrapping.MainRunner;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

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

    @GET
    @Path("/{recipeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipe(@PathParam("recipeId") String recipeId)
    {
        // TODO: Figure out how to integrate tapestry IOC with jersey so we don't have to do this.
        RecipeAppService recipeAppService = MainRunner.getRegistry().getService(RecipeAppService.class);

        Recipe recipe = recipeAppService.getRecipe(UUID.fromString(recipeId));

        if(recipe == null)
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response
            .status(Response.Status.OK)
            .entity(recipe)
            .build();
    }
}
