package applicationservices;

import aggregates.recipe.Recipe;

import java.util.List;
import java.util.UUID;

public interface RecipeAppService
{
    List<Recipe> getAll();
    Recipe getRecipe(UUID recipeId);
}
