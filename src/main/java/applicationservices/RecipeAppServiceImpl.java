package applicationservices;

import aggregates.recipe.Recipe;
import bootstrapping.VeganExpeditionModule;
import ddd.persistence.UnitOfWork;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.apache.tapestry5.ioc.annotations.Inject;
import repositories.recipe.RecipeRepository;

import java.util.List;

public class RecipeAppServiceImpl implements RecipeAppService
{
    @Inject
    private UnitOfWorkFactory unitOfWorkFactory;

    public void test()
    {
        try(UnitOfWork unitOfWork = unitOfWorkFactory.create())
        {
            RecipeRepository recipeRepository = unitOfWork.getRepository(RecipeRepository.class);

            List<Recipe> recipes = recipeRepository.getAll();

            System.out.println(recipes);
        }
    }
}
