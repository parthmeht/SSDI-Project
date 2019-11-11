import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {User} from '../model/user';
import {map} from 'rxjs/operators';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import { debounceTime, switchMap } from 'rxjs/operators';
import { distinctUntilChanged } from 'rxjs/operators';
import {Book} from '../model/book';

@Injectable()
export class UserService {
  private readonly usersUrl: string;
  private user: User;
  authenticated = false;
  public currentUser: Observable<User>;
  private currentUserSubject: BehaviorSubject<User>;

  constructor(private http: HttpClient, private router: Router) {
    this.usersUrl = 'http://localhost:8080/';
    this.user = JSON.parse(localStorage.getItem('currentUser'));
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
  search(terms: Observable<string>): Observable<User[]> {
    return terms.pipe(debounceTime(400), distinctUntilChanged(), switchMap(term => this.searchEntries(term)));
  }
  searchEntries(term): Observable<User[]>  {
    return this.http.get<User[]>(this.usersUrl + 'user/search?query=' + term, {
      headers: {
        authorization: 'Basic ' + this.user.authdata
      }
    });
  }
  currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  getUserById(id: number): Observable<User> {
    console.log('id: ' + id);
    console.log(this.currentUserSubject.value.authdata);
    return this.http.get<User>(this.usersUrl + 'userById/' + id, {
      headers: {
        authorization: 'Basic ' + this.currentUserSubject.value.authdata
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

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl + 'users', {
      headers: {
        authorization: 'Basic ' + this.user.authdata
      }
    });
  }
}
