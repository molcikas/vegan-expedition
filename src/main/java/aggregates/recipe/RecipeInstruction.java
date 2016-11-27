package aggregates.recipe;

import applicationservices.viewmodels.RecipeInstructionViewModel;
import ddd.exceptions.IllegalNegativeArgumentForDomainException;
import ddd.exceptions.IllegalNullArgumentForDomainException;
import ddd.exceptions.IllegalNullOrEmptyArgumentForDomainException;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class RecipeInstruction
{
    private UUID recipeInstructionId;

    private int stepNumber;

    private String description;

    public UUID getRecipeInstructionId()
    {
        return recipeInstructionId;
    }

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
            UUID.randomUUID(),
            0,
            recipeInstructionViewModel.description
        );
    }

    public RecipeInstruction(UUID recipeInstructionId, int stepNumber, String description)
    {
        if(recipeInstructionId == null)
        {
            throw new IllegalNullArgumentForDomainException("recipeInstructionId");
        }
        if(stepNumber < 0)
        {
            throw new IllegalNegativeArgumentForDomainException("stepNumber");
        }
        if(StringUtils.isBlank(description))
        {
            throw new IllegalNullOrEmptyArgumentForDomainException("description");
        }

        this.recipeInstructionId = recipeInstructionId;
        this.stepNumber = stepNumber;
        this.description = description;
    }

    void normalizeStepNumber(int stepNumber)
    {
        this.stepNumber = stepNumber;
    }
}
