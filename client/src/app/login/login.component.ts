import { Component, OnInit } from '@angular/core';
import {Book} from '../model/book';
import {UserService} from '../service/user.service';
import {User} from '../model/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  // email: string;
  // password: string;
  // user: User;
  email;
  password;
  constructor(private userService: UserService) {
    // his.user = new User();
  }

  ngOnInit() {
  }

  onSubmit() {
    this.userService.login(this.email, this.password).subscribe(
      res => {
        console.log(res);
        return res;
      },
      error => {
        console.log(error);
        return error;
      }
    );
  }

}
