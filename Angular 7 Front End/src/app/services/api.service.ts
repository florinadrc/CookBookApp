import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Recipe} from "../model/recipe";
import {RecipeCategory} from "../model/recipe-category";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private BASE_URL = "http://localhost:8080/recipes";

  constructor(private http: HttpClient) { }

  addRecipe(recipe: Recipe): Observable<any> {
    let url = `${this.BASE_URL}/create`;
    return this.http.post(url, recipe, httpOptions);
  }

  getRecipes(): Observable<Recipe[]> {
    let url = `${this.BASE_URL}/all`;
    return this.http.get<Recipe[]>(url);
  }

  getRecipesByCategory(recipeCategory: RecipeCategory): Observable<Recipe[]> {
    let url = `${this.BASE_URL}/all/by_category/${recipeCategory}`;
    return this.http.get<Recipe[]>(url);
  }

  getRecipe(id: number): Observable<Recipe> {
    let url = `${this.BASE_URL}/recipe/${id}`;
    return this.http.get<Recipe>(url);
  }

  updateRecipe(recipe: Recipe, id: number): Observable<any> {
    let url = `${this.BASE_URL}/update/${id}`;
    return this.http.post(url, recipe, httpOptions);
  }

  deleteRecipe(id: number): Observable<any> {
    let url = `${this.BASE_URL}/delete/${id}`;
    return this.http.delete(url);
  }

  searchRecipes(term: string): Observable<Recipe[]> {
    let url = `${this.BASE_URL}/all/by_name/?recipeName=${term}`;
    if(!term.trim())
      return of([]);
    return this.http.get<Recipe[]>(url);
  }
}
