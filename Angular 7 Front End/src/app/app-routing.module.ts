import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NewRecipeComponent} from "./new-recipe/new-recipe.component";
import {NotFoundComponent} from "./not-found/not-found.component";
import {HomeComponent} from "./home/home.component";
import {RecipeComponent} from "./recipe/recipe.component";
import {UpdateRecipeComponent} from "./update-recipe/update-recipe.component";
import {UserComponent} from "./user/user.component";
import {AdminComponent} from "./admin/admin.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'user', component: UserComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'auth/login', component: LoginComponent },
  { path: 'auth/login/signup', component: RegisterComponent },
  { path: 'recipe/:id', component: RecipeComponent },
  { path: 'new_recipe', component: NewRecipeComponent },
  { path: 'update_recipe/:id', component: UpdateRecipeComponent },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
