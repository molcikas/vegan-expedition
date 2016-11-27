package webapi.viewmodels;

import java.util.UUID;

public class RecipeIdOnly
{
    public final UUID recipeId;

    public RecipeIdOnly(UUID recipeId)
    {
        this.recipeId = recipeId;
    }
}
