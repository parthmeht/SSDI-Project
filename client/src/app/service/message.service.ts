import { Injectable } from '@angular/core';
import {User} from '../model/user';
import {HttpClient} from '@angular/common/http';
import {Book} from '../model/book';
import {Message} from '../model/message';

@Injectable()
export class MessageService {
  private readonly messageUrl: string;
  private user: User;
  constructor(private http: HttpClient) {
    this.messageUrl = 'http://localhost:8080/';
    this.user = JSON.parse(localStorage.getItem('currentUser'));
  }
  public save(message: Message) {
    const url = this.messageUrl + 'user/' + this.user.id + '/sendMessage';
    console.log('Inside Message Service');
    return this.http.post<Message>(url, message, {
      headers: {
        authorization: 'Basic ' + this.user.authdata,
      }
    });
  }

  public getinboxmessages() {
    const url = this.messageUrl + 'getmessages/' + this.user.id;
    console.log('Inside Message Service');
    return this.http.get<any>(url,{
      headers: {
        authorization: 'Basic ' + this.user.authdata,
      }
    });

  }
}
