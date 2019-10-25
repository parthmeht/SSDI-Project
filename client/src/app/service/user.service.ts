import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../model/user';

@Injectable()
export class UserService {
  private usersUrl: string;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080/';
  }

  public login(): Observable<User> {
    return this.http.get<User>(this.usersUrl + 'login');
  }

  public createNewUser(user: User) {
    console.log(user);
    return this.http.post<User>(this.usersUrl + 'registration', user);
  }
}
