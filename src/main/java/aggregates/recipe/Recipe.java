package aggregates.recipe;

import applicationservices.viewmodels.RecipeViewModel;
import ddd.AggregateRoot;
import ddd.exceptions.IllegalNegativeArgumentForDomainException;
import ddd.exceptions.IllegalNullArgumentForDomainException;
import ddd.exceptions.IllegalNullOrEmptyArgumentForDomainException;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ToString
public class Recipe extends AggregateRoot
{
    @Getter
    private UUID recipeId;

    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    private int prepTime;

    @Getter
    private int cookTime;

    @Getter
    private int servings;

    @Getter
    private boolean isVegetarian;

    @Getter
    private boolean isVegan;

    @Getter
    private boolean isPublished;

    @Getter
    private Integer points;

    @Getter
    private String credit;

    private List<RecipeIngredient> ingredients;

    private List<RecipeInstruction> instructions;

    public List<RecipeIngredient> getIngredients()
    {
        return Collections.unmodifiableList(ingredients);
    }

    public List<RecipeInstruction> getInstructions()
    {
        return Collections.unmodifiableList(instructions);
    }

    private Recipe()
    {
    }

    public Recipe(RecipeViewModel recipeViewModel)
    {
        this(
            recipeViewModel.recipeId != null ? recipeViewModel.recipeId : UUID.randomUUID(),
            recipeViewModel.name,
            recipeViewModel.description,
            recipeViewModel.prepTime,
            recipeViewModel.cookTime,
            recipeViewModel.servings,
            recipeViewModel.isVegetarian,
            recipeViewModel.isVegan,
            recipeViewModel.isPublished,
            recipeViewModel.points,
            recipeViewModel.credit,
            recipeViewModel
                .ingredients
                .stream()
                .map(RecipeIngredient::new)
                .collect(Collectors.toList()),
            recipeViewModel
                .instructions
                .stream()
                .map(RecipeInstruction::new)
                .collect(Collectors.toList())
        );

        normalizeSortIndexes();
    }

    public Recipe(
        UUID recipeId,
        String name,
        String description,
        int prepTime,
        int cookTime,
        int servings,
        boolean isVegetarian,
        boolean isVegan,
        boolean isPublished,
        Integer points,
        String credit,
        List<RecipeIngredient> ingredients,
        List<RecipeInstruction> instructions)
    {
        if(recipeId == null)
        {
            throw new IllegalNullArgumentForDomainException("recipeId");
        }
        if(StringUtils.isBlank(name))
        {
            throw new IllegalNullOrEmptyArgumentForDomainException("name");
        }
        if(prepTime < 0)
        {
            throw new IllegalNegativeArgumentForDomainException("prepTime");
        }
        if(cookTime < 0)
        {
            throw new IllegalNegativeArgumentForDomainException("cookTime");
        }
        if(servings < 0)
        {
            throw new IllegalNegativeArgumentForDomainException("servings");
        }

        this.recipeId = recipeId;
        this.name = name;
        this.description = description;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.servings = servings;
        this.isVegetarian = isVegetarian;
        this.isVegan = isVegan;
        this.isPublished = isPublished;
        this.points = points;
        this.credit = credit;
        this.ingredients = new ArrayList<>(ingredients);
        this.instructions = new ArrayList<>(instructions);
    }

    private void normalizeSortIndexes()
    {
        for(int i = 0; i < this.ingredients.size(); i++)
        {
            this.ingredients.get(i).normalizeOrderBy(i);
        }

        for(int i = 0; i < this.instructions.size(); i++)
        {
            this.instructions.get(i).normalizeStepNumber(i + 1);
        }
    }
}
