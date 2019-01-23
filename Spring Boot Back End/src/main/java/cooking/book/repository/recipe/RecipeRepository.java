package cooking.book.repository.recipe;

import cooking.book.model.recipe.Recipe;
import cooking.book.model.recipe.RecipeCategory;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Iterable<Recipe> findAllByCategory(RecipeCategory recipeCategory);
    Iterable<Recipe> findAllByNameContainingIgnoreCase(String recipeName);
    Iterable<Recipe> findAllByOrderByNoOfTimesAccessedDesc();
}
