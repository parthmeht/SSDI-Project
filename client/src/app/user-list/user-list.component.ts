import {Component, OnInit, ViewChild} from '@angular/core';
import {UserService} from '../service/user.service';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {User} from '../model/user';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'lastName', 'email', 'phoneNumber'];
  dataSource;
  users: User[];
  constructor(private userService: UserService) { }
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  ngOnInit() {
    this.userService.findAll().subscribe(data => {
      this.users = data;
      console.log(this.users);
      this.dataSource = new MatTableDataSource<User>(this.users);
      console.log(this.dataSource);
      this.dataSource.paginator = this.paginator;
    });
  }

}
