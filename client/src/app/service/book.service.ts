import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Book} from '../model/book';

@Injectable()
export class BookService {
  private booksUrl: string;

  constructor(private http: HttpClient) {
    this.booksUrl = 'http://localhost:8080/book/';
  }

  public findAll(): Observable<Book[]> {
    return this.http.get<Book[]>(this.booksUrl + 'allBooks');
  }

  public save(book: Book) {
    return this.http.post<Book>(this.booksUrl + 'add-book', book);
  }
}
