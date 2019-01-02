import {Component, OnInit} from '@angular/core';
import {ApiService} from "../services/api.service";
import {ActivatedRoute} from "@angular/router";
import {Recipe} from "../model/recipe";
import {Location} from "@angular/common";

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.css']
})
export class RecipeComponent implements OnInit {

  recipe: Recipe;

  constructor(
    private apiService: ApiService,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      let id= +params['id'];
      this.getRecipe();
    });
  }

  getRecipe(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.apiService.getRecipe(id).subscribe(recipe => this.recipe = recipe);
  }

  deleteThisRecipe(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    if(confirm("Are you sure you want to delete this recipe?")){
      this.apiService.deleteRecipe(id).subscribe(
        res => {
          alert("Recipe deleted successfully");
          this.location.back();
        },
        err => alert("Error occurred while deleting recipe")
      );
    }
  }
}
