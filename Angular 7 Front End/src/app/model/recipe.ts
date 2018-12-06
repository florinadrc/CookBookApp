import {RecipeCategory} from "./recipe-category";
import {Ingredient} from "./ingredient";

export interface Recipe {
  recipeId: number;
  recipeName: string;
  recipeCategory: RecipeCategory;
  ingredientsList: Ingredient[];
  instructions: string;
  suggestions: string;
}
