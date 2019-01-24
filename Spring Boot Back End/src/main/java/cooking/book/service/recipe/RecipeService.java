package cooking.book.service.recipe;

import cooking.book.model.recipe.Recipe;
import cooking.book.model.recipe.RecipeCategory;
import cooking.book.repository.recipe.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public RecipeCategory findCategory(String category) {
        RecipeCategory recipeCategory = RecipeCategory.STARTER;
        if(category.equals("1"))
            recipeCategory = RecipeCategory.MAIN_COURSE;
        if(category.equals("2"))
            recipeCategory = RecipeCategory.DESSERT;

        return recipeCategory;
    }

    public void newRecipe(Recipe recipe) {
        recipe.setLastAccessed(new Date());
        recipeRepository.save(recipe);
    }

    public Recipe getRecipe(long id) {
        Recipe recipeToSave = recipeRepository.findById(id).orElse(null);
        Recipe recipeToReturn = new Recipe();

        if (recipeToSave != null) {
            recipeToReturn.setRecipeId(recipeToSave.getRecipeId());
            copyRecipe(recipeToReturn, recipeToSave);
            recipeToSave.setLastAccessed(new Date());
            recipeToSave.setNoOfTimesAccessed(recipeToReturn.getNoOfTimesAccessed() + 1);

            recipeRepository.save(recipeToSave);

            return recipeToReturn;
        }
        return null;
    }

    public Recipe updateRecipe(Recipe recipe, long id) {
        Optional<Recipe> recipeToCheck = recipeRepository.findById(id);

        if (recipeToCheck.isPresent()) {
            Recipe recipeToSave = recipeToCheck.get();
            copyRecipe(recipeToSave, recipe);

            recipeRepository.save(recipeToSave);
            return recipeToSave;
        } else {
            return null;
        }
    }

    private void copyRecipe(Recipe recipe1, Recipe recipe2) {
        recipe1.setRecipeName(recipe2.getRecipeName());
        recipe1.setRecipeCategory(recipe2.getRecipeCategory());
        recipe1.setIngredientsList(recipe2.getIngredientsList());
        recipe1.setInstructions(recipe2.getInstructions());
        recipe1.setSuggestions(recipe2.getSuggestions());
        recipe1.setLastAccessed(recipe2.getLastAccessed());
        recipe1.setNoOfTimesAccessed(recipe2.getNoOfTimesAccessed());
    }
}
