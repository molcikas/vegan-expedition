package applicationservices;

import aggregates.recipe.Recipe;

import java.util.List;

public interface RecipeAppService
{
    List<Recipe> getAll();
}
