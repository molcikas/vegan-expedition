package aggregates.recipe;

import ddd.exceptions.IllegalNegativeArgumentForDomainException;
import ddd.exceptions.IllegalNullArgumentForDomainException;
import ddd.exceptions.IllegalNullOrEmptyArgumentForDomainException;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class RecipeIngredient
{
    private UUID recipeIngredientId;

    private boolean isRequired;

    private Double quantity;

    private String quantityUnit;

    private String quantityDetail;

    private String name;

    private String preparation;

    private Integer orderBy;

    public UUID getRecipeIngredientId()
    {
        return recipeIngredientId;
    }

    public boolean isRequired()
    {
        return isRequired;
    }

    public Double getQuantity()
    {
        return quantity;
    }

    public String getQuantityUnit()
    {
        return quantityUnit;
    }

    public String getQuantityDetail()
    {
        return quantityDetail;
    }

    public String getName()
    {
        return name;
    }

    public String getPreparation()
    {
        return preparation;
    }

    public Integer getOrderBy()
    {
        return orderBy;
    }

    public RecipeIngredient(UUID recipeIngredientId, boolean isRequired, Double quantity, String quantityUnit, String quantityDetail, String name, String preparation, int orderBy)
    {
        if(recipeIngredientId == null)
        {
            throw new IllegalNullArgumentForDomainException("recipeIngredientId");
        }
        if(quantity != null && quantity < 0)
        {
            throw new IllegalNegativeArgumentForDomainException("quantity");
        }
        if(StringUtils.isBlank(name))
        {
            throw new IllegalNullOrEmptyArgumentForDomainException("name");
        }

        this.recipeIngredientId = recipeIngredientId;
        this.isRequired = isRequired;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
        this.quantityDetail = quantityDetail;
        this.name = name;
        this.preparation = preparation;
        this.orderBy = orderBy;
    }
}
