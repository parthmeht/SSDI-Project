import { Component, OnInit } from '@angular/core';
import {Book} from '../model/book';
import {UserService} from '../service/user.service';
import {User} from '../model/user';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  submitted = false;
  credentials = {email: '', password: ''};

  constructor(private formBuilder: FormBuilder, private userService: UserService, private http: HttpClient, private router: Router) {}

 onSubmit() {
    this.submitted = true;
    if (this.loginForm.invalid) {
      return;
    } else {
      this.credentials.email = this.loginForm.controls.email.value;
      this.credentials.password = this.loginForm.controls.password.value;
      this.userService.authenticate(this.credentials).subscribe(
        res => {
          this.router.navigateByUrl('/');
        },
        err => {
          console.log(err);
          this.loginForm.controls.email.setErrors({'error': 'Invalid'});
          this.loginForm.controls.password.setErrors({'error': 'Invalid'});
        });
      return false;
    }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  get f() { return this.loginForm.controls; }

}
