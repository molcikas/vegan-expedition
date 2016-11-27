package applicationservices.viewmodels;

import aggregates.recipe.RecipeInstruction;

public class RecipeInstructionViewModel
{
    public String description;

    public RecipeInstructionViewModel()
    {
    }

    public RecipeInstructionViewModel(RecipeInstruction recipeInstruction)
    {
        this.description = recipeInstruction.getDescription();
    }
}
