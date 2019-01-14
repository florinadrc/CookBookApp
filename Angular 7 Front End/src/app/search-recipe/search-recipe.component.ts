import { Component, OnInit } from '@angular/core';
import {ApiService} from "../services/api.service";
import {Observable, Subject} from "rxjs";
import {Recipe} from "../model/recipe";
import {debounceTime, distinctUntilChanged, switchMap} from "rxjs/operators";

@Component({
  selector: 'app-search-recipe',
  templateUrl: './search-recipe.component.html',
  styleUrls: ['./search-recipe.component.css']
})
export class SearchRecipeComponent implements OnInit {

  recipess$: Observable<Recipe[]>;
  recipes$: Observable<Recipe[]>;
  private searchTerms = new Subject<string>();

  constructor(private apiService: ApiService) { }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.recipes$ = this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => this.apiService.searchRecipes(term)),
    );
  }

}
