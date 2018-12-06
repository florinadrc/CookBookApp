package cooking.book.dao;

import cooking.book.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientDAO extends CrudRepository<Ingredient, Long> {
}
