package webapi;

import applicationservices.viewmodels.RecipeViewModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("recipes")
public interface RecipeResource
{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getAll();

    @GET
    @Path("/{recipeId}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getRecipe(@PathParam("recipeId") String recipeId);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    Response addRecipe(RecipeViewModel recipeViewModel);

    @PUT
    @Path("/{recipeId}")
    Response updateRecipe(@PathParam("recipeId") String recipeId, RecipeViewModel recipeViewModel);

    @DELETE
    @Path("/{recipeId}")
    Response delete(@PathParam("recipeId") String recipeId);
}
