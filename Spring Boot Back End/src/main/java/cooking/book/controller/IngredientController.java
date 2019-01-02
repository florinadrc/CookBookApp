package cooking.book.controller;

import cooking.book.repos.IngredientRepository;
import cooking.book.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/recipes/ingredients")
@CrossOrigin
public class IngredientController {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }

    @PostMapping(value = "/create")
    public List<Ingredient> postIngredient(@RequestBody List<Ingredient> ingredientsList){
        return ingredientsList;
    }
}
