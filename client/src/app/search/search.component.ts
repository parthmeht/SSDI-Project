import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
  users: Array<User>;
  
  constructor() {
    this.users = new Array<User>();
    var user1 = new User();
    user1.name = 'Michael';
    user1.lastName = 'Pugh';
    this.users.push(user1);

    var user2 = new User();
    user2.name = 'Test';
    user2.lastName = 'User';
    this.users.push(user2);

    var user3 = new User();
    user3.name = 'Bob';
    user3.lastName = 'Tester';
    this.users.push(user3);
   }

  ngOnInit() {

  }

}
