package repositories.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "recipeingredient")
public class RecipeIngredient
{
    @Id
    @Column(columnDefinition = "BINARY(16)", length = 16)
    public UUID recipeIngredientId;

    @Column(columnDefinition = "BINARY(16)", length = 16)
    public UUID recipeId;

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

    public RecipeIngredient(UUID recipeIngredientId, UUID recipeId, boolean isRequired, Double quantity, String quantityUnit, String quantityDetail, String name, String preparation, int orderBy)
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
