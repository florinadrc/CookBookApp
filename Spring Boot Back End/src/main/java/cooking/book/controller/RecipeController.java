package cooking.book.controller;

import cooking.book.repos.RecipeRepository;
import cooking.book.model.Recipe;
import cooking.book.model.RecipeCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
        recipeRepository.save(recipe);
        return recipe;
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteRecipe(@PathVariable long id){
        recipeRepository.deleteById(id);
    }

    @GetMapping(value = "/recipe/{id}")
    public Recipe getRecipe(@PathVariable long id){
        return recipeRepository.findById(id).orElse(null);
    }

    @PostMapping(value = "/update/{id}")
    public Recipe updateRecipe(@RequestBody Recipe recipe, @PathVariable long id){
        Optional<Recipe> recipe1 = recipeRepository.findById(id);

        if(recipe1.isPresent()){
            Recipe _recipe = recipe1.get();
            _recipe.setRecipeName(recipe.getRecipeName());
            _recipe.setRecipeCategory(recipe.getRecipeCategory());
            _recipe.setIngredientsList(recipe.getIngredientsList());
            _recipe.setInstructions(recipe.getInstructions());
            _recipe.setSuggestions(recipe.getSuggestions());

            recipeRepository.save(_recipe);
            return _recipe;
        } else {
            return null;
        }
    }
}
