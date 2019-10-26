import { Component, OnInit, Input } from '@angular/core';
import { Book } from '../model/book';
import { User } from '../model/user';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.scss']
})
export class SearchResultComponent implements OnInit {
  @Input() book: Book;
  @Input() user: User;
  
  constructor() { }

  ngOnInit() {
  }

}
