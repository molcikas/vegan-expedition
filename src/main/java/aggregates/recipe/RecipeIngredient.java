package aggregates.recipe;

import applicationservices.viewmodels.RecipeIngredientViewModel;
import ddd.exceptions.IllegalArgumentForDomainException;
import ddd.exceptions.IllegalNegativeArgumentForDomainException;
import ddd.exceptions.IllegalNullOrEmptyArgumentForDomainException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.Fraction;

public class RecipeIngredient
{
    private boolean isRequired;

    private Fraction quantity;

    private String quantityUnit;

    private String quantityDetail;

    private String name;

    private String preparation;

    private Integer orderBy;

    public boolean isRequired()
    {
        return isRequired;
    }

    public Fraction getQuantity()
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
        Fraction quantityFraction;

        try
        {
            quantityFraction = StringUtils.isNotBlank(recipeIngredientViewModel.quantity) ? Fraction.getFraction(recipeIngredientViewModel.quantity) : null;
        }
        catch(Exception ex)
        {
            throw new IllegalArgumentForDomainException("Invalid fraction for quantity.", ex);
        }

        construct(
            recipeIngredientViewModel.isRequired,
            quantityFraction,
            recipeIngredientViewModel.quantityUnit,
            recipeIngredientViewModel.quantityDetail,
            recipeIngredientViewModel.name,
            recipeIngredientViewModel.preparation,
            0
        );
    }

    public RecipeIngredient(boolean isRequired, Fraction quantity, String quantityUnit, String quantityDetail, String name, String preparation, int orderBy)
    {
        construct(isRequired, quantity, quantityUnit, quantityDetail, name, preparation, orderBy);
    }

    private void construct(boolean isRequired, Fraction quantity, String quantityUnit, String quantityDetail, String name, String preparation, int orderBy)
    {
        if(quantity != null && quantity.floatValue() < 0.0)
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
