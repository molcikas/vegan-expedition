package applicationservices;

import aggregates.recipe.Recipe;
import ddd.persistence.UUIDExtensions;
import ddd.persistence.UnitOfWork;
import ddd.persistence.UnitOfWorkFactory;
import org.apache.tapestry5.ioc.annotations.Inject;
import repositories.recipe.RecipeRepository;

import java.util.List;
import java.util.UUID;

public class RecipeAppServiceImpl implements RecipeAppService
{
    @Inject
    private UnitOfWorkFactory unitOfWorkFactory;

    @Override
    public List<Recipe> getAll()
    {
        try(UnitOfWork unitOfWork = unitOfWorkFactory.create())
        {
            RecipeRepository recipeRepository = unitOfWork.getRepository(RecipeRepository.class);
            return recipeRepository.getAll();
        }
    }

    public Recipe getRecipe(UUID recipeId)
    {
        try(UnitOfWork unitOfWork = unitOfWorkFactory.create())
        {
            RecipeRepository recipeRepository = unitOfWork.getRepository(RecipeRepository.class);
            return recipeRepository.getRecipe(recipeId).orElse(null);
        }
    }
}
