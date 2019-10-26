import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../model/user';

@Injectable()
export class UserService {
  private usersUrl: string;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080/';
  }

  public login(email: string, password: string) {
    console.log(email + ' ' + password);
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/x-www-form-urlencoded',
      })
    };
    return this.http.post(this.usersUrl + 'login', {
      email, password
    }, httpOptions);
  }

  public createNewUser(user: User) {
    console.log(user);
    return this.http.post<User>(this.usersUrl + 'registration', user);
  }
}
