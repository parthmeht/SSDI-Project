import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Book} from '../model/book';
import { debounceTime, switchMap } from 'rxjs/operators';
import { distinctUntilChanged } from 'rxjs/operators';
import { User } from '../model/user';

@Injectable()
export class BookService {
  private booksUrl: string;
  private user: User
  constructor(private http: HttpClient) {
    this.booksUrl = 'http://localhost:8080/book/';
    this.user = JSON.parse(localStorage.getItem('currentUser'));
  }

  public findAll(): Observable<Book[]> {
    return this.http.get<Book[]>(this.booksUrl + 'allBooks');
  }

  public save(book: Book) {
    return this.http.post<Book>(this.booksUrl + 'add-book', book);
  }

  search(terms: Observable<string>) : Observable<Book[]> {
    return terms.pipe(debounceTime(400), distinctUntilChanged(), switchMap(term => this.searchEntries(term)));
  }

  searchEntries(term): Observable<Book[]>  {
    return this.http.get<Book[]>(this.booksUrl + 'search?query=' + term, {
      headers: {
        authorization: 'Basic ' + this.user.authdata
      }
    });
  }
}
