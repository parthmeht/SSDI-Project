import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../model/user';

@Injectable()
export class UserService {
  private usersUrl: string;
  authenticated = false;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080/';
  }

  public login(email: string, password: string) {
    console.log(email + ' ' + password);
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/x-www-form-urlencoded',
        Authorization: 'Basic ' + btoa(email + ':' + password)
  })
    };
    return this.http.post(this.usersUrl + 'login', {}, httpOptions);
  }

  public createNewUser(user: User) {
    console.log(user);
    return this.http.post<User>(this.usersUrl + 'registration', user);
  }

  authenticate(credentials, callback) {
    console.log(credentials);
    this.http.get(this.usersUrl + 'user', {
      headers: {
        authorization: this.createBasicAuthToken(credentials.email, credentials.password)
      }
    }).subscribe(response => {
      this.authenticated = !!response;
      return callback && callback();
    });

  }

  createBasicAuthToken(username: string, password: string) {
    return 'Basic ' + window.btoa(username + ':' + password);
  }
}
