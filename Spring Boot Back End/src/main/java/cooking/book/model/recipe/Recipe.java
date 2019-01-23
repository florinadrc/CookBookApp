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
    private long id;

    @NotBlank
    @Column(columnDefinition = "VARCHAR(255)")
    private String name;

    @Enumerated
    @Column(columnDefinition = "smallint")
    private RecipeCategory category;

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

    public Recipe(String name, RecipeCategory category, String instructions, String suggestions){
        this.name = name;
        this.category = category;
        this.instructions = instructions;
        this.suggestions = suggestions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecipeCategory getCategory() {
        return category;
    }

    public void setCategory(RecipeCategory category) {
        this.category = category;
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
