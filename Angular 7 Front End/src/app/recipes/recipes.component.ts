import { Component, OnInit } from '@angular/core';
import {ApiService} from '../services/api.service';
import {Recipe} from '../model/recipe';
import {RecipeCategory} from '../model/recipe-category';
import {OptionCategory} from '../model/option-category';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css']
})
export class RecipesComponent implements OnInit {

  recipes: Recipe[] = [];

  opts: OptionCategory[] = [
    { id: RecipeCategory.STARTER, name: 'Starters' },
    { id: RecipeCategory.MAIN_COURSE, name: 'Main Courses' },
    { id: RecipeCategory.DESSERT, name: 'Desserts' }
  ];

  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.getAllRecipes();
  }

  getAllRecipes() {
    this.apiService.getRecipes().subscribe(
      res => {
        this.recipes = res;
      },
      err => {
        alert('Error occurred while getting recipes from server');
      }
    );
  }

  getAllByCategory(recipeCategory: RecipeCategory) {
    this.apiService.getRecipesByCategory(recipeCategory).subscribe(
      res => {
        this.recipes = res;
      },
      err => {
        alert('Error occurred while getting recipes from server');
      }
    );
  }
}
