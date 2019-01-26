package cooking.book.controller.recipe;

import cooking.book.model.recipe.Ingredient;
import cooking.book.model.recipe.Recipe;
import cooking.book.model.recipe.RecipeCategory;
import cooking.book.repository.recipe.RecipeRepository;
import cooking.book.service.recipe.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeControllerTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    @Spy
    List<Recipe> recipes = new ArrayList<>();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        recipes =  getRecipeList();
    }

    @Test
    public void testDeleteRecipeWhenPresent() {
        Recipe recipe = recipes.get(0);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(recipe));
        doNothing().when(recipeRepository).deleteById(anyLong());

        assertEquals(recipeController.deleteRecipe(anyLong()), new ResponseEntity(HttpStatus.OK));

        verify(recipeRepository, atLeastOnce()).deleteById(anyLong());
    }

    @Test
    public void testDeleteRecipeWhenNotPresent() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertEquals(recipeController.deleteRecipe(anyLong()), new ResponseEntity(HttpStatus.NOT_FOUND));

        verify(recipeRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testGetRecipe() {
        Recipe recipe = recipes.get(0);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(recipe));
        when(recipeService.getRecipe(anyLong())).thenReturn(recipe);

        assertEquals(recipeController.getRecipe(anyLong()), new ResponseEntity<>(recipe, HttpStatus.OK));

        verify(recipeService, atLeastOnce()).getRecipe(anyLong());
    }

    private List<Recipe> getRecipeList() {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(0);
        recipe.setRecipeName("testName");
        recipe.setRecipeCategory(RecipeCategory.DESSERT);
        recipe.setInstructions("testInstructions");
        recipe.setSuggestions("testSuggestions");
        recipe.setLastAccessed(new Date());
        recipe.setNoOfTimesAccessed(0);
        recipe.getIngredientsList().add(new Ingredient("testIngredientName", "testIngredientQuantity"));

        recipes.add(recipe);

        return recipes;
    }
}
