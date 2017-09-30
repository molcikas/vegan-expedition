package repositories.recipe;

import aggregates.recipe.Recipe;
import ddd.persistence.AggregateRepository;
import ddd.persistence.PhotonTransactionContainer;
import com.github.molcikas.photon.PhotonTransaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RecipeRepository extends AggregateRepository<Recipe, PhotonTransactionContainer>
{
    private final PhotonTransaction photonTransaction;

    public RecipeRepository(PhotonTransactionContainer transactionContainer)
    {
        super(transactionContainer);
        this.photonTransaction = transactionContainer.getPhotonTransaction();
    }

    @Override
    public void add(Recipe recipe)
    {
        photonTransaction.insert(recipe);
        super.add(recipe);
    }

    @Override
    public void update(Recipe recipe)
    {
        photonTransaction.save(recipe);
        super.update(recipe);
    }

    public void delete(UUID recipeId)
    {
        Recipe recipe = photonTransaction.query(Recipe.class).fetchById(recipeId);
        photonTransaction.delete(recipe);
    }

    public List<Recipe> getAll()
    {
        return photonTransaction.query(Recipe.class).where("1=1").fetchList();
    }

    public Optional<Recipe> getRecipe(UUID recipeId)
    {
        Recipe recipe = photonTransaction.query(Recipe.class).fetchById(recipeId);
        return Optional.ofNullable(recipe);
    }
}
