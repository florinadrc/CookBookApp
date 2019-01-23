package cooking.book.model.recipe;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "recipe_id")
    private long recipeId;

    @NotBlank
    @Column(columnDefinition = "VARCHAR(255)")
    private String recipeName;

    @Enumerated
    @Column(columnDefinition = "smallint")
    private RecipeCategory recipeCategory;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    private List<Ingredient> ingredientsList = new ArrayList<>();

    @NotBlank
    @Column(columnDefinition = "LONGTEXT")
    private String instructions;

    @Column(columnDefinition = "LONGTEXT")
    private String suggestions;

    private Date lastAccessed;

    private long noOfTimesAccessed;

    public Recipe(){}

    public Recipe(String recipeName, RecipeCategory recipeCategory, String instructions, String suggestions){
        this.recipeName = recipeName;
        this.recipeCategory = recipeCategory;
        this.instructions = instructions;
        this.suggestions = suggestions;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public RecipeCategory getRecipeCategory() {
        return recipeCategory;
    }

    public void setRecipeCategory(RecipeCategory recipeCategory) {
        this.recipeCategory = recipeCategory;
    }

    public List<Ingredient> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredient> ingredientsList) {
        this.ingredientsList.clear();
        if(ingredientsList != null)
            this.ingredientsList.addAll(ingredientsList);
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public Date getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(Date lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public long getNoOfTimesAccessed() {
        return noOfTimesAccessed;
    }

    public void setNoOfTimesAccessed(long noOfTimesAccessed) {
        this.noOfTimesAccessed = noOfTimesAccessed;
    }
}
