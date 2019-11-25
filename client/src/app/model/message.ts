import {User} from './user';

export class Message {
  id: bigint;
  title: string;
  body: string;
  senderId: User;
  receiverId: User;
  createdDate: Date;
}
