package applicationservices;

import aggregates.recipe.Recipe;
import applicationservices.viewmodels.RecipeViewModel;
import ddd.persistence.UnitOfWork;
import ddd.persistence.UnitOfWorkFactory;
import repositories.recipe.RecipeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class RecipeAppServiceImpl implements RecipeAppService
{
    private final UnitOfWorkFactory unitOfWorkFactory;

    public RecipeAppServiceImpl(UnitOfWorkFactory unitOfWorkFactory)
    {
        this.unitOfWorkFactory = unitOfWorkFactory;
    }

    @Override
    public List<RecipeViewModel> getAll()
    {
        try(UnitOfWork unitOfWork = unitOfWorkFactory.create())
        {
            RecipeRepository recipeRepository = unitOfWork.getRepository(RecipeRepository.class);
            return recipeRepository
                .getAll()
                .stream()
                .map(RecipeViewModel::new)
                .collect(Collectors.toList());
        }
    }

    @Override
    public RecipeViewModel getRecipe(UUID recipeId)
    {
        try(UnitOfWork unitOfWork = unitOfWorkFactory.create())
        {
            RecipeRepository recipeRepository = unitOfWork.getRepository(RecipeRepository.class);
            Optional<Recipe> recipeOptional = recipeRepository.getRecipe(recipeId);
            return recipeOptional.isPresent() ? new RecipeViewModel(recipeOptional.get()) : null;
        }
    }

    @Override
    public UUID addRecipe(RecipeViewModel recipeViewModel)
    {
        Recipe recipe = new Recipe(recipeViewModel);

        try(UnitOfWork unitOfWork = unitOfWorkFactory.create())
        {
            RecipeRepository recipeRepository = unitOfWork.getRepository(RecipeRepository.class);
            recipeRepository.add(recipe);

            unitOfWork.commit();
        }

        return recipe.getRecipeId();
    }

    @Override
    public void updateRecipe(RecipeViewModel recipeViewModel)
    {
        Recipe recipe = new Recipe(recipeViewModel);

        try(UnitOfWork unitOfWork = unitOfWorkFactory.create())
        {
            RecipeRepository recipeRepository = unitOfWork.getRepository(RecipeRepository.class);
            recipeRepository.update(recipe);

            unitOfWork.commit();
        }
    }

    @Override
    public void delete(UUID recipeId)
    {
        try(UnitOfWork unitOfWork = unitOfWorkFactory.create())
        {
            RecipeRepository recipeRepository = unitOfWork.getRepository(RecipeRepository.class);
            recipeRepository.delete(recipeId);

            unitOfWork.commit();
        }
    }
}
