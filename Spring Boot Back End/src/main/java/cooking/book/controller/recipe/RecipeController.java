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
        return recipeRepository.findAllByCategory(recipeCategory);
    }

    @GetMapping(value = "/all/byName/{term}")
    public Iterable<Recipe> getAllByName(@PathVariable String term){
        return recipeRepository.findAllByNameContainingIgnoreCase(term);
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
        Recipe recipeToSave = recipeRepository.findById(id).orElse(null);
        Recipe recipeToReturn = new Recipe();

        if (recipeToSave != null) {
            recipeToReturn.setId(recipeToSave.getId());
            copyRecipe(recipeToReturn, recipeToSave);
            recipeToSave.setLastAccessed(new Date());
            recipeToSave.setNoOfTimesAccessed(recipeToReturn.getNoOfTimesAccessed() + 1);
        }

        recipeRepository.save(recipeToSave);

        return recipeToReturn;
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
        recipe1.setName(recipe2.getName());
        recipe1.setCategory(recipe2.getCategory());
        recipe1.setIngredientsList(recipe2.getIngredientsList());
        recipe1.setInstructions(recipe2.getInstructions());
        recipe1.setSuggestions(recipe2.getSuggestions());
        recipe1.setLastAccessed(recipe2.getLastAccessed());
        recipe1.setNoOfTimesAccessed(recipe2.getNoOfTimesAccessed());
    }
}
