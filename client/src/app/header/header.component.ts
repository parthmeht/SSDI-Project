import { Component, OnInit } from '@angular/core';
import {UserService} from '../service/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  user: {};
  constructor(private userService: UserService) { }

  ngOnInit() {
    this.user = this.userService.currentUserValue();
  }

  logout() {
    console.log('Logout called');
    this.userService.logout();
  }
}
