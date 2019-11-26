import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { UserService } from '../service/user.service';
import { User } from '../model/user';

@Component({
  selector: 'app-user-search',
  templateUrl: './user-search.component.html',
  styleUrls: ['./user-search.component.scss'],
  providers: [UserService]
})
export class UserSearchComponent implements OnInit {
  results: User[];
  searchTerm = new Subject<string>();
  allUsers: User[];
  searching = false;

  constructor(private userService: UserService) {
    this.userService.search(this.searchTerm)
    .subscribe(results => {
      console.log(results);
      this.searching = false;
      this.results = results;
    });
  }

  search(){
    console.log('we searching');
    this.searching = true;
  }

  ngOnInit() {
    this.userService.findAll().subscribe(data => {
      this.allUsers = data;
      console.log(this.allUsers);
    });
  }

}
