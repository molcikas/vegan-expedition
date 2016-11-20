package repositories.recipe;

import aggregates.recipe.Recipe;
import ddd.persistence.AggregateRepository;
import ddd.persistence.JpaTransactionContainer;
import ddd.persistence.UUIDExtensions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RecipeRepository extends AggregateRepository<Recipe, JpaTransactionContainer>
{
    private final EntityManager entityManager;

    public RecipeRepository(JpaTransactionContainer transactionContainer)
    {
        super(transactionContainer);
        this.entityManager = transactionContainer.getEntityManager();
    }

    public List<Recipe> getAll()
    {
        List<repositories.entities.Recipe> recipes = entityManager.createQuery("FROM Recipe ORDER BY name").getResultList();

        return RecipeMapper.toDomainEntities(recipes);
    }

    public Optional<Recipe> getRecipe(UUID recipeId)
    {
        List<repositories.entities.Recipe> recipes = entityManager
            .createQuery("FROM Recipe WHERE recipeId = :recipeId")
            .setParameter("recipeId", recipeId)
            .getResultList();

        return recipes.size() > 0 ? Optional.of(RecipeMapper.toDomainEntity(recipes.get(0))) : Optional.empty();
    }
}
