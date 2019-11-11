import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Book} from '../model/book';
import { debounceTime, switchMap } from 'rxjs/operators';
import { distinctUntilChanged } from 'rxjs/operators';
import { User } from '../model/user';
// import {type} from 'os';

@Injectable()
export class BookService {
  private booksUrl: string;
  private user: User;
  constructor(private http: HttpClient) {
    this.booksUrl = 'http://localhost:8080/';
    this.user = JSON.parse(localStorage.getItem('currentUser'));
  }

  public findAll(): Observable<Book[]> {
    return this.http.get<Book[]>(this.booksUrl + 'books', {
      headers: {
        authorization: 'Basic ' + this.user.authdata
      }
    });
  }
  public fetchAllBook(): Observable<Book[]> {
    const bookurl = 'http://localhost:8080/user/' + this.user.id + '/books/';
    return this.http.get<Book[]>(bookurl, {
      headers: {
        authorization: 'Basic ' + this.user.authdata
      }
    }) ;
  }

  public save(book: Book) {
    const bookurl = 'http://localhost:8080/user/' + this.user.id + '/addBook/';
    return this.http.post<Book>(bookurl, book, {
      headers: {
        authorization: 'Basic ' + this.user.authdata,
      }
    }) ;
     // return this.http.post<Book>(this.booksUrl + 'add-book', book);
  }

  search(terms: Observable<string>): Observable<Book[]> {
    return terms.pipe(debounceTime(400), distinctUntilChanged(), switchMap(term => this.searchEntries(term)));
  }

  searchEntries(term): Observable<Book[]>  {
    return this.http.get<Book[]>(this.booksUrl + 'book/search?query=' + term, {
      headers: {
        authorization: 'Basic ' + this.user.authdata
      }
    });
  }
}
