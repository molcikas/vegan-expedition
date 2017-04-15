package repositories.recipe;

import aggregates.recipe.Recipe;
import ddd.persistence.AggregateRepository;
import ddd.persistence.PhotonTransactionContainer;
import photon.PhotonConnection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RecipeRepository extends AggregateRepository<Recipe, PhotonTransactionContainer>
{
    private final PhotonConnection photonConnection;

    public RecipeRepository(PhotonTransactionContainer transactionContainer)
    {
        super(transactionContainer);
        this.photonConnection = transactionContainer.getPhotonConnection();
    }

    @Override
    public void add(Recipe recipe)
    {
        photonConnection.insert(recipe);
        super.add(recipe);
    }

    @Override
    public void update(Recipe recipe)
    {
        photonConnection.save(recipe);
        super.update(recipe);
    }

    public void delete(UUID recipeId)
    {
        Recipe recipe = photonConnection.query(Recipe.class).fetchById(recipeId);
        photonConnection.delete(recipe);
    }

    public List<Recipe> getAll()
    {
        return photonConnection.query(Recipe.class).fetchByIdsQuery("SELECT recipeId FROM recipe").fetchList();
    }

    public Optional<Recipe> getRecipe(UUID recipeId)
    {
        Recipe recipe = photonConnection.query(Recipe.class).fetchById(recipeId);
        return Optional.ofNullable(recipe);
    }
}
