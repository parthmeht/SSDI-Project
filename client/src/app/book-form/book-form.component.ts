import { Component, OnInit } from '@angular/core';
import {Book} from '../model/book';
import {ActivatedRoute, Router} from '@angular/router';
import {BookService} from '../service/book.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-book-form',
  templateUrl: './book-form.component.html',
  styleUrls: ['./book-form.component.scss']
})
export class BookFormComponent implements OnInit {

  book: Book;
  showTextBox = false;
  submitted = false;
  isChecked = false;
  bookForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private route: ActivatedRoute, private router: Router, private bookService: BookService) {
    this.book = new Book();
  }

  onSubmit() {
    console.log(this.bookForm);
    this.submitted = true;
    this.book.title = this.bookForm.controls.title.value;
    this.book.author = this.bookForm.controls.author.value;
    this.book.isListed = this.bookForm.controls.isListed.value;
    this.book.price = this.bookForm.controls.price.value;
    this.bookService.save(this.book).subscribe(result => this.gotoBookList());
  }

  gotoBookList() {
    this.router.navigate(['/books']);
  }

  ngOnInit(): void {
    this.bookForm = this.formBuilder.group({
      title: ['', Validators.required],
      author: ['', Validators.required],
      isListed: ['', Validators.nullValidator],
      price: ['', Validators.nullValidator]
    });
  }

  onCheckboxClick($event) {
   this.showTextBox = !this.showTextBox;
   console.log(this.isChecked);
  }
}
