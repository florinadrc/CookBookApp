package cooking.book.service.recipe;

import cooking.book.model.recipe.Ingredient;
import cooking.book.model.recipe.Recipe;
import cooking.book.model.recipe.RecipeCategory;
import cooking.book.repository.recipe.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    @Spy
    private List<Recipe> recipes = new ArrayList<>();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        recipes = getRecipeList();
    }

    @Test
    public void testNewRecipe() {
        Recipe recipe = recipes.get(0);

        when(recipeRepository.save(recipe)).thenReturn(recipe);

        recipeService.newRecipe(recipe);

        verify(recipeRepository, atLeastOnce()).save(recipe);
    }

    @Test
    public void testGetRecipe() {
        Recipe recipe = recipes.get(0);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(recipe));
        when(recipeRepository.save(recipe)).thenReturn(recipe);

        Recipe recipeToReturn = recipeService.getRecipe(anyLong());
        assertEquals(recipeToReturn.getRecipeId(), 0);

        verify(recipeRepository, atLeastOnce()).findById(anyLong());
        verify(recipeRepository, atLeastOnce()).save(recipe);
    }

    @Test
    public void testUpdateRecipe() {
        Recipe recipe = recipes.get(0);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(recipe));
        when(recipeRepository.save(recipe)).thenReturn(recipe);

        assertEquals(recipeService.updateRecipe(recipe,anyLong()), recipe);

        verify(recipeRepository, atLeastOnce()).findById(anyLong());
        verify(recipeRepository, atLeastOnce()).save(recipe);
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