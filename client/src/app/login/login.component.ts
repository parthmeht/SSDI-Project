import { Component, OnInit } from '@angular/core';
import {Book} from '../model/book';
import {UserService} from '../service/user.service';
import {User} from '../model/user';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  credentials = {email: '', password: ''};
  constructor(private userService: UserService, private http: HttpClient, private router: Router) {}

  ngOnInit() {
  }

 onSubmit() {
    console.log(this.credentials);
    this.userService.authenticate(this.credentials, () => {
     this.router.navigateByUrl('/').then(r => console.log(r));
   });
    return false;
  }

}
