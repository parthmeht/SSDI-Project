import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Book} from '../model/book';
import { BookService } from '../service/book.service';
import { UserService } from '../service/user.service';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from '../model/user';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss'],
  encapsulation: ViewEncapsulation.None

})
export class UserProfileComponent implements OnInit {
  bookCollection: Book[];
  booksInterestedIn: Book[];
  user: User;

  book: Book;
  showTextBox = false;
  submitted = false;
  isChecked = false;
  bookForm: FormGroup;
  private routeSub: Subscription;
  isMyProfile = false;
  closeResult: string;

  constructor(private bookService: BookService, private userService: UserService, private route: ActivatedRoute,
              private modalService: NgbModal, private formBuilder: FormBuilder) {
    this.book = new Book();
  }

  ngOnInit() {

    this.bookForm = this.formBuilder.group({
      title: ['', Validators.required],
      author: ['', Validators.required],
      isListed: ['', Validators.nullValidator],
      price: ['', Validators.nullValidator]
    });
    this.bookService.fetchAllBook().subscribe(data => {
      this.booksInterestedIn = data;
      this.bookCollection = data;
    });
    this.routeSub = this.route.params.subscribe(params => {
      console.log(params);
      console.log(params.id);
      if (params && params.hasOwnProperty('id')) {
        this.userService.getUserById(params.id).subscribe(data => {
          this.user = data;
        });
      } else {
        this.isMyProfile = true;
        this.user = this.userService.currentUserValue();
      }
    });
  }

  // tslint:disable-next-line:use-lifecycle-interface
  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }


  onSubmit() {
    console.log(this.bookForm);
    this.submitted = true;
    this.book.title = this.bookForm.controls.title.value;
    this.book.author = this.bookForm.controls.author.value;
    this.book.isListed = this.bookForm.controls.isListed.value;
    this.book.price = this.bookForm.controls.price.value;
    // this.bookService.save(this.book).subscribe(result => this.gotoBookList());
  }
  openModal(content) {
    this.modalService.open(content, { centered: true });
  }
  onCheckboxClick($event) {
    this.showTextBox = !this.showTextBox;
    console.log(this.isChecked);
  }
}
