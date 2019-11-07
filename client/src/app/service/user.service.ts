import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {User} from '../model/user';
import {map} from 'rxjs/operators';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';

@Injectable()
export class UserService {
  private usersUrl: string;
  authenticated = false;
  public currentUser: Observable<User>;
  private currentUserSubject: BehaviorSubject<User>;

  constructor(private http: HttpClient, private router: Router) {
    this.usersUrl = 'http://localhost:8080/';
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public login(email: string, password: string) {
    console.log(email + ' ' + password);
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/x-www-form-urlencoded',
        Authorization: 'Basic ' + btoa(email + ':' + password)
  })
    };
    return this.http.post(this.usersUrl + 'user', {}, httpOptions);
  }

  public createNewUser(user: User) {
    console.log(user);
    return this.http.post<User>(this.usersUrl + 'registration', user);
  }

  authenticate(credentials) {
    console.log(credentials);
    return this.http.get(this.usersUrl + 'user', {
      headers: {
        authorization: this.createBasicAuthToken(credentials.email, credentials.password)
      }
    }).pipe(map((response: any) => {
      // store user details and basic auth credentials in local storage to keep user logged in between page refreshes
      console.log(response);
      response.user.authdata = window.btoa(credentials.email + ':' + credentials.password);
      localStorage.setItem('currentUser', JSON.stringify(response.user));
      this.currentUserSubject.next(response.user);
      return response.user;
    }));
  }

  currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  getUserById(id: number): Observable<User> {
    console.log('id: ' + id);
    return this.http.get<User>(this.usersUrl + 'userById/' + id, { 
      headers: {
        authorization: this.currentUserSubject.value.authdata
      }
    });
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

  createBasicAuthToken(username: string, password: string) {
    return 'Basic ' + window.btoa(username + ':' + password);
  }
}
