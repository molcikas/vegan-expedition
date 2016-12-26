package aggregates.recipe;

import applicationservices.viewmodels.RecipeInstructionViewModel;
import ddd.exceptions.IllegalNegativeArgumentForDomainException;
import ddd.exceptions.IllegalNullOrEmptyArgumentForDomainException;
import org.apache.commons.lang3.StringUtils;

public class RecipeInstruction
{
    private int stepNumber;

    private String description;

    public int getStepNumber()
    {
        return stepNumber;
    }

    public String getDescription()
    {
        return description;
    }

    public RecipeInstruction(RecipeInstructionViewModel recipeInstructionViewModel)
    {
        this(
            0,
            recipeInstructionViewModel.description
        );
    }

    public RecipeInstruction(int stepNumber, String description)
    {
        if(stepNumber < 0)
        {
            throw new IllegalNegativeArgumentForDomainException("stepNumber");
        }
        if(StringUtils.isBlank(description))
        {
            throw new IllegalNullOrEmptyArgumentForDomainException("description");
        }

        this.stepNumber = stepNumber;
        this.description = description;
    }

    void normalizeStepNumber(int stepNumber)
    {
        this.stepNumber = stepNumber;
    }
}
