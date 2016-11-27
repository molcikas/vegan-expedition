package applicationservices.viewmodels;

import aggregates.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RecipeViewModel
{
    public UUID recipeId;

    public String name;

    public String description;

    public int prepTime;

    public int cookTime;

    public int servings;

    public boolean isVegetarian;

    public boolean isVegan;

    public boolean isPublished;

    public String credit;

    public List<RecipeIngredientViewModel> ingredients;

    public List<RecipeInstructionViewModel> instructions;

    public RecipeViewModel()
    {
        this.ingredients = new ArrayList<>();
        this.instructions = new ArrayList<>();
    }

    public RecipeViewModel(Recipe recipe)
    {
        this.recipeId = recipe.getRecipeId();
        this.name = recipe.getName();
        this.description = recipe.getDescription();
        this.prepTime = recipe.getPrepTime();
        this.cookTime = recipe.getCookTime();
        this.servings = recipe.getServings();
        this.isVegetarian = recipe.isVegetarian();
        this.isVegan = recipe.isVegan();
        this.isPublished = recipe.isPublished();
        this.credit = recipe.getCredit();

        this.ingredients = recipe
            .getIngredients()
            .stream()
            .map(RecipeIngredientViewModel::new)
            .collect(Collectors.toList());

        this.instructions = recipe
            .getInstructions()
            .stream()
            .map(RecipeInstructionViewModel::new)
            .collect(Collectors.toList());
    }
}
