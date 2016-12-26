package aggregates.recipe;

import applicationservices.viewmodels.RecipeIngredientViewModel;
import ddd.exceptions.IllegalNegativeArgumentForDomainException;
import ddd.exceptions.IllegalNullOrEmptyArgumentForDomainException;
import org.apache.commons.lang3.StringUtils;

public class RecipeIngredient
{
    private boolean isRequired;

    private Double quantity;

    private String quantityUnit;

    private String quantityDetail;

    private String name;

    private String preparation;

    private Integer orderBy;

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

    public RecipeIngredient(RecipeIngredientViewModel recipeIngredientViewModel)
    {
        this(
            recipeIngredientViewModel.isRequired,
            recipeIngredientViewModel.quantity,
            recipeIngredientViewModel.quantityUnit,
            recipeIngredientViewModel.quantityDetail,
            recipeIngredientViewModel.name,
            recipeIngredientViewModel.preparation,
            0
        );
    }

    public RecipeIngredient(boolean isRequired, Double quantity, String quantityUnit, String quantityDetail, String name, String preparation, int orderBy)
    {
        if(quantity != null && quantity < 0)
        {
            throw new IllegalNegativeArgumentForDomainException("quantity");
        }
        if(StringUtils.isBlank(name))
        {
            throw new IllegalNullOrEmptyArgumentForDomainException("name");
        }

        this.isRequired = isRequired;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
        this.quantityDetail = quantityDetail;
        this.name = name;
        this.preparation = preparation;
        this.orderBy = orderBy;
    }

    void normalizeOrderBy(int orderBy)
    {
        this.orderBy = orderBy;
    }
}
