package applicationservices;

import aggregates.recipe.Recipe;
import ddd.persistence.UnitOfWork;
import ddd.persistence.UnitOfWorkFactory;
import org.apache.tapestry5.ioc.annotations.Inject;
import repositories.recipe.RecipeRepository;

import java.util.List;

public class RecipeAppServiceImpl implements RecipeAppService
{
    @Inject
    private UnitOfWorkFactory unitOfWorkFactory;

    public List<Recipe> getAll()
    {
        try(UnitOfWork unitOfWork = unitOfWorkFactory.create())
        {
            RecipeRepository recipeRepository = unitOfWork.getRepository(RecipeRepository.class);
            return recipeRepository.getAll();
        }
    }
}
