package repositories.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "recipeinstruction")
public class RecipeInstruction
{
    @Id
    @Column(columnDefinition = "BINARY(16)", length = 16)
    public UUID recipeInstructionId;

    @Column(columnDefinition = "BINARY(16)", length = 16)
    public UUID recipeId;

    @Column
    public int stepNumber;

    @Column
    public String description;

    public RecipeInstruction()
    {
    }

    public RecipeInstruction(UUID recipeId, int stepNumber, String description)
    {
        this.recipeInstructionId = UUID.randomUUID();
        this.recipeId = recipeId;
        this.stepNumber = stepNumber;
        this.description = description;
    }
}
