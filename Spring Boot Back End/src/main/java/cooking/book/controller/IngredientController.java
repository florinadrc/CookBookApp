package cooking.book.controller;

import cooking.book.dao.IngredientDAO;
import cooking.book.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/recipes/ingredients")
@CrossOrigin
public class IngredientController {

    private IngredientDAO ingredientDAO;

    @Autowired
    public IngredientController(IngredientDAO ingredientDAO){
        this.ingredientDAO = ingredientDAO;
    }

    @PostMapping(value = "/create")
    public List<Ingredient> postIngredient(@RequestBody List<Ingredient> ingredientsList){
        return ingredientsList;
    }
}
