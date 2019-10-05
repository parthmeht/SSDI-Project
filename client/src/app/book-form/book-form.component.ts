import { Component, OnInit } from '@angular/core';
import {Book} from '../model/book';
import {ActivatedRoute, Router} from '@angular/router';
import {BookService} from '../service/book.service';

@Component({
  selector: 'app-book-form',
  templateUrl: './book-form.component.html',
  styleUrls: ['./book-form.component.css']
})
export class BookFormComponent implements OnInit {

  book: Book;

  constructor(private route: ActivatedRoute, private router: Router, private bookService: BookService) {
    this.book = new Book();
  }

  onSubmit() {
    this.bookService.save(this.book).subscribe(result => this.gotoBookList());
  }

  gotoBookList() {
    this.router.navigate(['/books']);
  }

  ngOnInit(): void {
  }


}
