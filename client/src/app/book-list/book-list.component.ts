import {Component, OnInit, ViewChild} from '@angular/core';
import {Book} from '../model/book';
import { BookService } from '../service/book.service';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'title', 'author', 'isListed', 'price', 'user_email', 'user_name', 'user_lastName', 'user_phoneNumber'];
  dataSource;
  books: Book[];
  constructor(private bookService: BookService) {
  }
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  ngOnInit() {
    this.bookService.findAll().subscribe(data => {
      this.books = data;
      console.log(this.books);
      this.dataSource = new MatTableDataSource<Book>(this.books);
      console.log(this.dataSource);
      this.dataSource.paginator = this.paginator;
    });
  }
}
