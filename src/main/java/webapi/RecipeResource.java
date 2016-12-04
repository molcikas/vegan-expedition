package webapi;

import applicationservices.RecipeAppService;
import applicationservices.RecipeAppServiceImpl;
import applicationservices.viewmodels.RecipeViewModel;
import bootstrapping.MainRunner;
import bootstrapping.RegistryContainer;
import ddd.persistence.UnitOfWorkFactory;
import webapi.viewmodels.RecipeIdOnly;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("recipes")
public class RecipeResource
{
    private RecipeAppService getRecipeAppService()
    {
        // TODO: Figure out how to integrate tapestry IOC with jersey so we don't have to do this.
        return new RecipeAppServiceImpl(RegistryContainer.getRegistry().getService(UnitOfWorkFactory.class));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll()
    {
        RecipeAppService recipeAppService = getRecipeAppService();
        List<RecipeViewModel> recipes = recipeAppService.getAll();

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
        RecipeAppService recipeAppService = getRecipeAppService();
        RecipeViewModel recipe = recipeAppService.getRecipe(UUID.fromString(recipeId));

        if(recipe == null)
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response
            .status(Response.Status.OK)
            .entity(recipe)
            .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRecipe(RecipeViewModel recipeViewModel)
    {
        RecipeAppService recipeAppService = getRecipeAppService();
        UUID recipeId = recipeAppService.addRecipe(recipeViewModel);

        return Response
            .status(Response.Status.OK)
            .entity(new RecipeIdOnly(recipeId))
            .build();
    }

    @PUT
    @Path("/{recipeId}")
    public Response updateRecipe(@PathParam("recipeId") String recipeId, RecipeViewModel recipeViewModel)
    {
        RecipeAppService recipeAppService = getRecipeAppService();
        recipeAppService.updateRecipe(recipeViewModel);

        return Response
            .status(Response.Status.OK)
            .build();
    }

    @DELETE
    @Path("/{recipeId}")
    public Response delete(@PathParam("recipeId") String recipeId)
    {
        RecipeAppService recipeAppService = getRecipeAppService();
        recipeAppService.delete(UUID.fromString(recipeId));

        return Response
            .status(Response.Status.OK)
            .build();
    }
}
