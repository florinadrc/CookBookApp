import {Component, OnInit} from '@angular/core';
import {Recipe} from '../model/recipe';
import {RecipeCategory} from '../model/recipe-category';
import {ApiService} from '../services/api.service';
import {Location} from '@angular/common';
import {Ingredient} from '../model/ingredient';
import {OptionCategory} from '../model/option-category';

@Component({
  selector: 'app-new-recipe',
  templateUrl: './new-recipe.component.html',
  styleUrls: ['./new-recipe.component.css']
})
export class NewRecipeComponent implements OnInit {

  model: Recipe = {
    recipeId: 0,
    recipeName: '',
    recipeCategory: RecipeCategory.STARTER,
    ingredientsList: [],
    instructions: '',
    suggestions: '',
    lastAccessed: '',
    noOfTimesAccessed: 0
  };

  ingredient: Ingredient = {
    ingredientId: 0,
    ingredientName: '',
    quantity: ''
  };

  opts: OptionCategory[] = [
    { id: RecipeCategory.STARTER, name: 'Starter' },
    { id: RecipeCategory.MAIN_COURSE, name: 'Main Course' },
    { id: RecipeCategory.DESSERT, name: 'Dessert' }
  ];

  filterCategory(theCategory: RecipeCategory) {
    this.model.recipeCategory = theCategory;
  }

  constructor(
    private apiService: ApiService,
    private location: Location) { }

  ngOnInit() {
  }

  sendRecipe(): void {
    this.apiService.addRecipe(this.model).subscribe(
      res => {
        this.location.back();
      },
      err => {
        alert('An error has occurred while sending recipe details');
      }
    );
  }

  addToIngredientsList(): void {
    this.model.ingredientsList.push(JSON.parse(JSON.stringify(this.ingredient)));
  }

}
