import {Component, Input, OnInit} from '@angular/core';
import {Recipe} from '../model/recipe';
import {ApiService} from '../services/api.service';
import {ActivatedRoute} from '@angular/router';
import {Ingredient} from '../model/ingredient';
import {RecipeCategory} from '../model/recipe-category';
import {OptionCategory} from '../model/option-category';
import {Location} from '@angular/common';

@Component({
  selector: 'app-update-recipe',
  templateUrl: './update-recipe.component.html',
  styleUrls: ['./update-recipe.component.css']
})
export class UpdateRecipeComponent implements OnInit {

  @Input() recipe: Recipe;

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
    this.recipe.recipeCategory = theCategory;
  }

  constructor(
    private apiService: ApiService,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  ngOnInit() {
    this.getRecipe();
  }

  getRecipe(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.apiService.getRecipe(id)
      .subscribe(recipe => this.recipe = recipe);
  }

  addToIngredientsList(): void {
    this.recipe.ingredientsList.push(JSON.parse(JSON.stringify(this.ingredient)));
  }

  deleteFromIngredientsList(ingredient: Ingredient) {
    const indexOfIngredient = this.recipe.ingredientsList.indexOf(ingredient);
    this.recipe.ingredientsList.splice(indexOfIngredient, 1);
  }

  sendRecipe(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.apiService.updateRecipe(this.recipe, id).subscribe(
      res => {
        this.location.back();
      },
      err => {
        alert('An error has occurred while updating recipe');
      }
    );
  }
}
