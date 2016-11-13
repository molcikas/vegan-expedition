package repositories.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "recipeinstruction")
public class RecipeInstruction
{
    @Id
    public byte[] recipeInstructionId;

    @Column
    public byte[] recipeId;

    @Column
    public int stepNumber;

    @Column
    public String description;

    public RecipeInstruction()
    {
    }

    public RecipeInstruction(byte[] recipeInstructionId, byte[] recipeId, int stepNumber, String description)
    {
        this.recipeInstructionId = recipeInstructionId;
        this.recipeId = recipeId;
        this.stepNumber = stepNumber;
        this.description = description;
    }
}
