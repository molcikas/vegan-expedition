package applicationservices;

import applicationservices.viewmodels.RecipeViewModel;

import java.util.List;
import java.util.UUID;

public interface RecipeAppService
{
    List<RecipeViewModel> getAll();
    RecipeViewModel getRecipe(UUID recipeId);
    UUID addRecipe(RecipeViewModel recipeViewModel);
    void updateRecipe(RecipeViewModel recipeViewModel);
    void delete(UUID recipeId);
}
