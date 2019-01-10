import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from "../auth/token-storage.service";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  private roles: string[];
  private authority: string;
  private info: any;

  constructor(private tokenStorage: TokenStorageService) {}

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every(role => {
        if (role === 'ADMIN') {
          this.authority = 'admin';
          return false;
        }
        this.authority = 'user';
        return true;
      });
    }

    this.info = {
      token: this.tokenStorage.getToken(),
      username: this.tokenStorage.getUsername(),
      authorities: this.tokenStorage.getAuthorities()
    };
  }

  logout() {
    this.tokenStorage.signOut();
    window.location.reload();
  }
}
