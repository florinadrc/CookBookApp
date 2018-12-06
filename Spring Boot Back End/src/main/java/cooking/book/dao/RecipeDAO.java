package cooking.book.dao;

import cooking.book.model.Recipe;
import cooking.book.model.RecipeCategory;
import org.springframework.data.repository.CrudRepository;

public interface RecipeDAO extends CrudRepository<Recipe, Long> {
    Iterable<Recipe> findAllByRecipeCategory(RecipeCategory recipeCategory);
    Iterable<Recipe> findAllByRecipeName(String recipeName);
}
