import { Component } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {UserService} from './service/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title: string;
  authenticated = false;
  credentials = {email: '', password: ''};

  constructor(private userService: UserService, private http: HttpClient, private router: Router) {
    this.title = 'Book Application';
    this.userService.authenticate(this.credentials);
  }
}
