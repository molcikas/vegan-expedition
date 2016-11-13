package repositories.recipe;

import aggregates.recipe.Recipe;
import ddd.persistence.AggregateRepository;
import ddd.persistence.JpaTransactionContainer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

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
        List<repositories.entities.Recipe> recipes = entityManager.createQuery("FROM Recipe").getResultList();

        return RecipeMapper.toDomainEntities(recipes);
    }
}
