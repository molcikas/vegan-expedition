package aggregates.recipe;

import ddd.AggregateRoot;
import ddd.exceptions.IllegalArgumentForDomainException;
import ddd.exceptions.IllegalNegativeArgumentForDomainException;
import ddd.exceptions.IllegalNullArgumentForDomainException;
import ddd.exceptions.IllegalNullOrEmptyArgumentForDomainException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Recipe extends AggregateRoot
{
    private UUID recipeId;

    private String name;

    private String description;

    private int prepTime;

    private int cookTime;

    private int servings;

    private boolean isVegetarian;

    private boolean isVegan;

    private boolean isPublished;

    private String credit;

    private List<RecipeIngredient> ingredients;

    private List<RecipeInstruction> instructions;

    public UUID getRecipeId()
    {
        return recipeId;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public int getPrepTime()
    {
        return prepTime;
    }

    public int getCookTime()
    {
        return cookTime;
    }

    public int getServings()
    {
        return servings;
    }

    public boolean isVegetarian()
    {
        return isVegetarian;
    }

    public boolean isVegan()
    {
        return isVegan;
    }

    public boolean isPublished()
    {
        return isPublished;
    }

    public String getCredit()
    {
        return credit;
    }

    public List<RecipeIngredient> getIngredients()
    {
        return Collections.unmodifiableList(ingredients);
    }

    public List<RecipeInstruction> getInstructions()
    {
        return Collections.unmodifiableList(instructions);
    }

    public Recipe(UUID recipeId, String name, String description, int prepTime, int cookTime, int servings, boolean isVegetarian, boolean isVegan, boolean isPublished, String credit, List<RecipeIngredient> ingredients, List<RecipeInstruction> instructions)
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
        this.credit = credit;
        this.ingredients = new ArrayList<>(ingredients);
        this.instructions = new ArrayList<>(instructions);
    }
}
