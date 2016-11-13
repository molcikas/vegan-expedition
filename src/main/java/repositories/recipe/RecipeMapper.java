package repositories.recipe;

import aggregates.recipe.Recipe;
import aggregates.recipe.RecipeIngredient;
import aggregates.recipe.RecipeInstruction;
import ddd.persistence.UUIDExtensions;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapper
{
    public static Recipe toDomainEntity(repositories.entities.Recipe databaseRecipe)
    {
        return new Recipe(
            UUIDExtensions.fromBytes(databaseRecipe.recipeId),
            databaseRecipe.name,
            databaseRecipe.description,
            databaseRecipe.prepTime,
            databaseRecipe.cookTime,
            databaseRecipe.servings,
            databaseRecipe.isVegetarian,
            databaseRecipe.isVegan,
            databaseRecipe.isPublished,
            databaseRecipe.credit,
            databaseRecipe
                .ingredients
                .stream()
                .map(ingredient -> new RecipeIngredient(
                    UUIDExtensions.fromBytes(ingredient.recipeIngredientId),
                    ingredient.isRequired,
                    ingredient.quantity,
                    ingredient.quantityUnit,
                    ingredient.quantityDetail,
                    ingredient.name,
                    ingredient.preparation,
                    ingredient.orderBy
                ))
                .collect(Collectors.toList()),
            databaseRecipe
                .instructions
                .stream()
                .map(instruction -> new RecipeInstruction(
                    UUIDExtensions.fromBytes(instruction.recipeInstructionId),
                    instruction.stepNumber,
                    instruction.description
                ))
                .collect(Collectors.toList())
        );
    }

    public static List<Recipe> toDomainEntities(List<repositories.entities.Recipe> databaseRecipes)
    {
        return databaseRecipes
            .stream()
            .map(RecipeMapper::toDomainEntity)
            .collect(Collectors.toList());
    }

    public static repositories.entities.Recipe toDatabaseEntity(Recipe recipe)
    {
        return new repositories.entities.Recipe(
            UUIDExtensions.toBytes(recipe.getRecipeId()),
            recipe.getName(),
            recipe.getDescription(),
            recipe.getPrepTime(),
            recipe.getCookTime(),
            recipe.getServings(),
            recipe.isVegetarian(),
            recipe.isVegan(),
            recipe.isPublished(),
            recipe.getCredit(),
            recipe
                .getIngredients()
                .stream()
                .map(ingredient -> new repositories.entities.RecipeIngredient(
                    UUIDExtensions.toBytes(ingredient.getRecipeIngredientId()),
                    UUIDExtensions.toBytes(recipe.getRecipeId()),
                    ingredient.isRequired(),
                    ingredient.getQuantity(),
                    ingredient.getQuantityUnit(),
                    ingredient.getQuantityDetail(),
                    ingredient.getName(),
                    ingredient.getPreparation(),
                    ingredient.getOrderBy()
                ))
                .collect(Collectors.toSet()),
            recipe
                .getInstructions()
                .stream()
                .map(instruction -> new repositories.entities.RecipeInstruction(
                    UUIDExtensions.toBytes(instruction.getRecipeInstructionId()),
                    UUIDExtensions.toBytes(recipe.getRecipeId()),
                    instruction.getStepNumber(),
                    instruction.getDescription()
                ))
                .collect(Collectors.toSet())
        );
    }

    public static List<repositories.entities.Recipe> toDatabaseEntities(List<Recipe> recipies)
    {
        return recipies
            .stream()
            .map(RecipeMapper::toDatabaseEntity)
            .collect(Collectors.toList());
    }
}
