package webapi;

import applicationservices.RecipeAppService;
import applicationservices.viewmodels.RecipeViewModel;
import org.apache.tapestry5.ioc.annotations.Inject;
import webapi.viewmodels.RecipeIdOnly;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

public class RecipeResourceImpl implements RecipeResource
{
    @Inject
    private RecipeAppService recipeAppService;

    @Override
    public Response getAll()
    {
        List<RecipeViewModel> recipes = recipeAppService.getAll();

        return Response
            .status(Response.Status.OK)
            .entity(recipes)
            .build();
    }

    @Override
    public Response getRecipe(String recipeId)
    {
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

    @Override
    public Response addRecipe(RecipeViewModel recipeViewModel)
    {
        UUID recipeId = recipeAppService.addRecipe(recipeViewModel);

        return Response
            .status(Response.Status.OK)
            .entity(new RecipeIdOnly(recipeId))
            .build();
    }

    @Override
    public Response updateRecipe(String recipeId, RecipeViewModel recipeViewModel)
    {
        recipeAppService.updateRecipe(recipeViewModel);

        return Response
            .status(Response.Status.OK)
            .build();
    }

    @Override
    public Response delete(String recipeId)
    {
        recipeAppService.delete(UUID.fromString(recipeId));

        return Response
            .status(Response.Status.OK)
            .build();
    }
}
