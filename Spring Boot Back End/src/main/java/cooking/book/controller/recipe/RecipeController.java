package cooking.book.controller.recipe;

import cooking.book.repository.recipe.RecipeRepository;
import cooking.book.model.recipe.Recipe;
import cooking.book.model.recipe.RecipeCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "/all/byAccesses")
    public Iterable<Recipe> getAllByNoOfAccesses(){
        return recipeRepository.findAllByOrderByNoOfTimesAccessedDesc();
    }

    @GetMapping(value = "/all/byCategory/{theCategory}")
    public Iterable<Recipe> getAllByCategory(@PathVariable String theCategory){
        RecipeCategory recipeCategory = RecipeCategory.STARTER;
        if(theCategory.equals("1"))
            recipeCategory = RecipeCategory.MAIN_COURSE;
        if(theCategory.equals("2"))
            recipeCategory = RecipeCategory.DESSERT;
        return recipeRepository.findAllByRecipeCategory(recipeCategory);
    }

    @GetMapping(value = "/all/byName/{term}")
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
    public ResponseEntity deleteRecipe(@PathVariable long id){
        if (recipeRepository.findById(id).isPresent()) {
            recipeRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id){
        Recipe recipeToSave = recipeRepository.findById(id).orElse(null);
        Recipe recipeToReturn = new Recipe();

        if (recipeToSave != null) {
            recipeToReturn.setRecipeId(recipeToSave.getRecipeId());
            copyRecipe(recipeToReturn, recipeToSave);
            recipeToSave.setLastAccessed(new Date());
            recipeToSave.setNoOfTimesAccessed(recipeToReturn.getNoOfTimesAccessed() + 1);

            recipeRepository.save(recipeToSave);

            return new ResponseEntity<>(recipeToReturn, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/update/{id}")
    public Recipe updateRecipe(@RequestBody Recipe recipe, @PathVariable long id){
        Optional<Recipe> recipeToCheck = recipeRepository.findById(id);

        if(recipeToCheck.isPresent()){
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
