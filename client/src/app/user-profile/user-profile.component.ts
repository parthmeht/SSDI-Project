import { Component, OnInit } from '@angular/core';
import {Book} from '../model/book';
import { BookService } from '../service/book.service';
import { UserService } from '../service/user.service';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from '../model/user';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  bookCollection: Book[];
  booksInterestedIn: Book[];
  user: User;
  private routeSub: Subscription;
  isMyProfile = false;

  constructor(private bookService: BookService, private userService: UserService, private route: ActivatedRoute) {
   }

  ngOnInit() {
    this.bookService.findAll().subscribe(data => {
      this.booksInterestedIn = data;
      this.bookCollection = data;
    });
    this.routeSub = this.route.params.subscribe(params => {
      console.log(params);
      console.log(params['id']);
      if(params && params.hasOwnProperty('id')){
        this.userService.getUserById(params['id']).subscribe(data => {
          this.user = data;
        });
      }else{
        this.isMyProfile = true;
        this.user = this.userService.currentUserValue();
      }
    })
  }

  ngOnDestrooy(){
    this.routeSub.unsubscribe();
  }
}
