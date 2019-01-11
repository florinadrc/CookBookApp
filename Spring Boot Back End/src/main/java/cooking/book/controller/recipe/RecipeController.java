package cooking.book.controller.recipe;

import cooking.book.repository.recipe.RecipeRepository;
import cooking.book.model.recipe.Recipe;
import cooking.book.model.recipe.RecipeCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(value = "/recipes")
@CrossOrigin
public class RecipeController {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Recipe> getAll(){
        return recipeRepository.findAll();
    }

    @GetMapping(value = "/all/by_accesses")
    public Iterable<Recipe> getAllByNoOfAccesses(){
        return recipeRepository.findAllByOrderByNoOfTimesAccessedDesc();
    }

    @GetMapping(value = "/all/by_category/{theCategory}")
    public Iterable<Recipe> getAllByCategory(@PathVariable String theCategory){
        RecipeCategory recipeCategory = RecipeCategory.STARTER;
        if(theCategory.equals("1"))
            recipeCategory = RecipeCategory.MAIN_COURSE;
        if(theCategory.equals("2"))
            recipeCategory = RecipeCategory.DESSERT;
        return recipeRepository.findAllByRecipeCategory(recipeCategory);
    }

    @GetMapping(value = "/all/by_name/{term}")
    public Iterable<Recipe> getAllByName(@PathVariable String term){
        return recipeRepository.findAllByRecipeNameContainingIgnoreCase(term);
    }

    @PostMapping(value = "/create")
    public Recipe postRecipe(@RequestBody Recipe recipe){
        recipe.setLastAccessed(new Date());
        recipeRepository.save(recipe);
        return recipe;
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteRecipe(@PathVariable long id){
        recipeRepository.deleteById(id);
    }

    @GetMapping(value = "/recipe/{id}")
    public Recipe getRecipe(@PathVariable long id){
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        Recipe recipe1 = new Recipe();

        if (recipe != null) {
            recipe1.setRecipeId(recipe.getRecipeId());
            copyRecipe(recipe1, recipe);
            recipe.setLastAccessed(new Date());
            recipe.setNoOfTimesAccessed(recipe1.getNoOfTimesAccessed() + 1);
        }

        recipeRepository.save(recipe);

        return recipe1;
    }

    @PostMapping(value = "/update/{id}")
    public Recipe updateRecipe(@RequestBody Recipe recipe, @PathVariable long id){
        Optional<Recipe> recipe1 = recipeRepository.findById(id);

        if(recipe1.isPresent()){
            Recipe _recipe = recipe1.get();
            copyRecipe(_recipe, recipe);

            recipeRepository.save(_recipe);
            return _recipe;
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
