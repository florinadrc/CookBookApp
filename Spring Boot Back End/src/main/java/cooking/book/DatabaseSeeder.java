package cooking.book;

import cooking.book.dao.RecipeDAO;
import cooking.book.model.Ingredient;
import cooking.book.model.Recipe;
import cooking.book.model.RecipeCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


//@Component
public class DatabaseSeeder implements CommandLineRunner {

    private RecipeDAO recipeDAO;

    @Autowired
    public DatabaseSeeder(RecipeDAO recipeDAO) {
        this.recipeDAO = recipeDAO;
    }


    @Override
    public void run(String... args) throws Exception {
        Recipe recipe1 = new Recipe("Work please", RecipeCategory.STARTER, "Thanks", "Thank you please");
        recipe1.getIngredientsList().add(new Ingredient("Un ingredient", "Greu"));
        recipe1.getIngredientsList().add(new Ingredient("Doi ingredient", "Usor"));
        recipeDAO.save(recipe1);

        Recipe recipe2 = new Recipe("Work please", RecipeCategory.MAIN_COURSE, "Thanks", "Thank you please");
        recipe2.getIngredientsList().add(new Ingredient("Un ingredient", "Greu"));
        recipe2.getIngredientsList().add(new Ingredient("Doi ingredient", "Usor"));
        recipeDAO.save(recipe2);

        Recipe recipe3 = new Recipe("Work please", RecipeCategory.DESSERT, "Thanks", "Thank you please");
        recipe3.getIngredientsList().add(new Ingredient("Un ingredient", "Greu"));
        recipe3.getIngredientsList().add(new Ingredient("Doi ingredient", "Usor"));
        recipeDAO.save(recipe3);
    }
}