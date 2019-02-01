import { Component, OnInit } from '@angular/core';
import {ApiService} from '../services/api.service';
import {Recipe} from '../model/recipe';

@Component({
  selector: 'app-popular',
  templateUrl: './popular.component.html',
  styleUrls: ['./popular.component.css']
})
export class PopularComponent implements OnInit {

  recipes: Recipe[] = [];

  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.apiService.getRecipesByNoOfAccesses().subscribe(
      res => {
        this.recipes = res;
      },
      err => {
        alert('Error occurred while getting recipes');
      }
    );
  }

}
