package cooking.book;

import cooking.book.repos.RecipeRepository;
import cooking.book.model.Ingredient;
import cooking.book.model.Recipe;
import cooking.book.model.RecipeCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;


//@Component
public class DatabaseSeeder implements CommandLineRunner {

    private RecipeRepository recipeRepository;

    @Autowired
    public DatabaseSeeder(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        Recipe recipe1 = new Recipe("Work please", RecipeCategory.STARTER, "Thanks", "Thank you please");
        recipe1.getIngredientsList().add(new Ingredient("Un ingredient", "Greu"));
        recipe1.getIngredientsList().add(new Ingredient("Doi ingredient", "Usor"));
        recipeRepository.save(recipe1);

        Recipe recipe2 = new Recipe("Work please", RecipeCategory.MAIN_COURSE, "Thanks", "Thank you please");
        recipe2.getIngredientsList().add(new Ingredient("Un ingredient", "Greu"));
        recipe2.getIngredientsList().add(new Ingredient("Doi ingredient", "Usor"));
        recipeRepository.save(recipe2);

        Recipe recipe3 = new Recipe("Work please", RecipeCategory.DESSERT, "Thanks", "Thank you please");
        recipe3.getIngredientsList().add(new Ingredient("Un ingredient", "Greu"));
        recipe3.getIngredientsList().add(new Ingredient("Doi ingredient", "Usor"));
        recipeRepository.save(recipe3);
    }
}