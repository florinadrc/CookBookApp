import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationComponent } from './navigation/navigation.component';
import { NewRecipeComponent } from './new-recipe/new-recipe.component';
import { RecipesComponent } from './recipes/recipes.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { RecipeComponent } from './recipe/recipe.component';
import { HomeComponent } from './home/home.component';
import { UpdateRecipeComponent } from './update-recipe/update-recipe.component';
import { SearchRecipeComponent } from './search-recipe/search-recipe.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    NewRecipeComponent,
    RecipesComponent,
    NotFoundComponent,
    RecipeComponent,
    HomeComponent,
    UpdateRecipeComponent,
    SearchRecipeComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
