import { Component, OnInit } from '@angular/core';
import {Book} from '../model/book';
import { BookService } from '../service/book.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  bookCollection: Book[];
  booksInterestedIn: Book[];
  //Placeholders until user backend is set up
  user = {
    FirstName: 'Michael'
  }
  isMyProfile = false;

  constructor(private bookService: BookService) { }

  ngOnInit() {
    this.bookService.findAll().subscribe(data => {
      this.booksInterestedIn = data;
      this.bookCollection = data;
    });
  }
}
