package repositories.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "recipeingredient")
public class RecipeIngredient
{
    @Id
    public byte[] recipeIngredientId;

    @Column
    public byte[] recipeId;

    @Column
    public boolean isRequired;

    @Column
    public Double quantity;

    @Column
    public String quantityUnit;

    @Column
    public String quantityDetail;

    @Column
    public String name;

    @Column
    public String preparation;

    @Column
    public int orderBy;

    public RecipeIngredient()
    {
    }

    public RecipeIngredient(byte[] recipeIngredientId, byte[] recipeId, boolean isRequired, Double quantity, String quantityUnit, String quantityDetail, String name, String preparation, int orderBy)
    {
        this.recipeIngredientId = recipeIngredientId;
        this.recipeId = recipeId;
        this.isRequired = isRequired;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
        this.quantityDetail = quantityDetail;
        this.name = name;
        this.preparation = preparation;
        this.orderBy = orderBy;
    }
}
