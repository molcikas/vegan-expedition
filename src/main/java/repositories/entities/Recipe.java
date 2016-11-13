package repositories.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "recipe")
public class Recipe
{
    @Id
    public byte[] recipeId;

    @Column
    public String name;

    @Column
    public String description;

    @Column
    public int prepTime;

    @Column
    public int cookTime;

    @Column
    public int servings;

    @Column
    public boolean isVegetarian;

    @Column
    public boolean isVegan;

    @Column
    public boolean isPublished;

    @Column
    public String credit;

    @OneToMany(mappedBy = "recipeId", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("name ASC")
    public Set<RecipeIngredient> ingredients;

    @OneToMany(mappedBy = "recipeId", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("stepNumber ASC")
    public Set<RecipeInstruction> instructions;

    public Recipe()
    {
    }

    public Recipe(byte[] recipeId, String name, String description, int prepTime, int cookTime, int servings, boolean isVegetarian, boolean isVegan, boolean isPublished, String credit, Set<RecipeIngredient> ingredients, Set<RecipeInstruction> instructions)
    {
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
        this.ingredients = ingredients;
        this.instructions = instructions;
    }
}
