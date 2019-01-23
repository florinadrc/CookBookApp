package cooking.book.repository.recipe;

import cooking.book.model.recipe.Recipe;
import cooking.book.model.recipe.RecipeCategory;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Iterable<Recipe> findAllByRecipeCategory(RecipeCategory recipeCategory);
    Iterable<Recipe> findAllByRecipeNameContainingIgnoreCase(String recipeName);
    Iterable<Recipe> findAllByOrderByNoOfTimesAccessedDesc();
}
