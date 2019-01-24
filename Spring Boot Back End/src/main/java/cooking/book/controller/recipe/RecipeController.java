package cooking.book.controller.recipe;

import cooking.book.model.recipe.Recipe;
import cooking.book.repository.recipe.RecipeRepository;
import cooking.book.service.recipe.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/recipes")
@CrossOrigin
public class RecipeController {

    private RecipeRepository recipeRepository;
    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository, RecipeService recipeService){
        this.recipeRepository = recipeRepository;
        this.recipeService = recipeService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Recipe> getAll(){
        return recipeRepository.findAll();
    }

    @GetMapping(value = "/all/byAccesses")
    public List<Recipe> getAllByNoOfAccesses(){
        return recipeRepository.findAllByOrderByNoOfTimesAccessedDesc();
    }

    @GetMapping(value = "/all/byCategory/{theCategory}")
    public List<Recipe> getAllByCategory(@PathVariable String theCategory){
        return recipeRepository.findAllByRecipeCategory(recipeService.findCategory(theCategory));
    }

    @GetMapping(value = "/all/byName/{term}")
    public List<Recipe> getAllByName(@PathVariable String term){
        return recipeRepository.findAllByRecipeNameContainingIgnoreCase(term);
    }

    @PostMapping(value = "/create")
    public Recipe postRecipe(@RequestBody Recipe recipe){
        recipeService.newRecipe(recipe);
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
        if (recipeRepository.findById(id).isPresent())
            return new ResponseEntity<>(recipeService.getRecipe(id), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/update/{id}")
    public Recipe updateRecipe(@RequestBody Recipe recipe, @PathVariable long id){
        return recipeService.updateRecipe(recipe, id);
    }
}
