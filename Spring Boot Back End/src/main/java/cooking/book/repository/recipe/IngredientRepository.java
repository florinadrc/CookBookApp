package cooking.book.repository.recipe;

import cooking.book.model.recipe.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
