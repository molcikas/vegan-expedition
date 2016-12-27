package applicationservices.viewmodels;


import aggregates.recipe.RecipeIngredient;

public class RecipeIngredientViewModel
{
    public boolean isRequired;

    public String quantity;

    public String quantityUnit;

    public String quantityDetail;

    public String name;

    public String preparation;

    public RecipeIngredientViewModel()
    {
    }

    public RecipeIngredientViewModel(RecipeIngredient recipeIngredient)
    {
        this.isRequired = recipeIngredient.isRequired();
        this.quantity = recipeIngredient.getQuantity() != null ? recipeIngredient.getQuantity().toProperString() : "";
        this.quantityUnit = recipeIngredient.getQuantityUnit();
        this.quantityDetail = recipeIngredient.getQuantityDetail();
        this.name = recipeIngredient.getName();
        this.preparation = recipeIngredient.getPreparation();
    }
}
