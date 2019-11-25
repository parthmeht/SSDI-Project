import { Component, OnInit, Input } from '@angular/core';
import { Book } from '../model/book';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {BookService} from '../service/book.service';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.scss']
})
export class BookComponent implements OnInit {
  @Input() book: Book;
  @Input() fullView: boolean;
  modalReference: any;
  bookForm: FormGroup;
  showTextBox = false;
  submitted = false;
  isChecked = false;
  constructor(private bookService: BookService, private modalService: NgbModal, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.bookForm = this.formBuilder.group({
      title: ['', Validators.required],
      author: ['', Validators.required],
      isListed: ['', Validators.nullValidator],
      price: ['', Validators.nullValidator]
    });
  }

  updateBook(content) {
    this.modalReference = this.modalService.open(content, { centered: true });
    this.bookForm.controls.title.setValue(this.book.title);
    this.bookForm.controls.author.setValue(this.book.author);
    this.bookForm.controls.isListed.setValue(this.book.isListed);
    this.bookForm.controls.price.setValue(this.book.price);
    this.showTextBox = this.book.isListed;
  }
  onSubmit() {
    this.submitted = true;
    this.book.title = this.bookForm.controls.title.value;
    this.book.author = this.bookForm.controls.author.value;
    this.book.isListed = this.bookForm.controls.isListed.value;
    this.book.price = this.bookForm.controls.price.value;
    this.bookService.update(this.book).subscribe(result => {
      this.modalReference.close();
    });
  }

  onCheckboxClick($event) {
    this.showTextBox = !this.showTextBox;

    console.log(this.isChecked);
  }

}
