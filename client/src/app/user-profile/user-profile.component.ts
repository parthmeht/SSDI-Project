import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Book} from '../model/book';
import { BookService } from '../service/book.service';
import { UserService } from '../service/user.service';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from '../model/user';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Message} from '../model/message';
import {MessageService} from '../service/message.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss'],
  encapsulation: ViewEncapsulation.None

})
export class UserProfileComponent implements OnInit {
  bookCollection: Book[] = [];
  booksInterestedIn: Book[] = [];
  user: User;
  message: Message;
  book: Book;
  loggedInUser: User;
  showTextBox = false;
  submitted = false;
  isChecked = false;
  bookForm: FormGroup;
  messageForm: FormGroup;
  private routeSub: Subscription;
  isMyProfile = false;
  closeResult: string;
  modalReference: any;

  constructor(private bookService: BookService, private userService: UserService, private route: ActivatedRoute,
              private modalService: NgbModal, private formBuilder: FormBuilder, private messageService: MessageService) {
    this.book = new Book();
    this.user = new User();
    this.message = new Message();
    this.loggedInUser = new User();
  }

  ngOnInit() {

    this.bookForm = this.formBuilder.group({
      title: ['', Validators.required],
      author: ['', Validators.required],
      isListed: ['', Validators.nullValidator],
      price: ['', Validators.nullValidator]
    });
    this.messageForm = this.formBuilder.group({
      message_title: ['', Validators.required],
      message_body: ['', Validators.required]
    });
    this.bookService.fetchAllBook().subscribe(data => {
      this.booksInterestedIn = data;
      this.bookCollection = data;
    });
    this.routeSub = this.route.params.subscribe(params => {
      if (params && params.hasOwnProperty('id')) {
        this.userService.getUserById(params.id).subscribe(data => {
          this.user = data;
          this.loggedInUser = this.userService.currentUserValue();
        });
      } else {
        this.isMyProfile = true;
        this.user = this.userService.currentUserValue();
        this.loggedInUser = this.user;
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
    this.bookService.save(this.book).subscribe(result => {
      this.modalReference.close();
      this.bookService.fetchAllBook().subscribe(data => {
        this.booksInterestedIn = data;
        this.bookCollection = data;
      });
    });
  }
  openModal(content) {
    this.modalReference = this.modalService.open(content, { centered: true });
  }
  onCheckboxClick($event) {
    this.showTextBox = !this.showTextBox;
    console.log(this.isChecked);
  }

  onMessageSubmit() {
    console.log(this.messageForm);
    console.log('Inside On Message Submit');
    // this.loggedInUser = this.userService.currentUserValue();
    this.submitted = true;
    this.message.title = this.messageForm.controls.message_title.value;
    this.message.body = this.messageForm.controls.message_body.value;
    this.message.senderId = this.loggedInUser;
    this.message.receiverId = this.user;
    console.log(this.message);
    this.messageService.save(this.message).subscribe(result => {
      this.modalReference.close();
    });
  }
}
