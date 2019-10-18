import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {BookListComponent} from './book-list/book-list.component';
import {BookFormComponent} from './book-form/book-form.component';
import {UserProfileComponent} from './user-profile/user-profile.component';
import {HomeComponent} from './home/home.component';
import {LayoutComponent} from './layout/layout.component';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './sign-up/sign-up.component';

// Routes are set up this way to allow header and footer for all views except login and sign up
const routes: Routes = [
  { 
    path: '', 
    component: LayoutComponent,
    children: [
  { path: '', component: HomeComponent },
  { path: 'books', component: BookListComponent },
  { path: 'addBook', component: BookFormComponent },
  { path: 'user', component: UserProfileComponent },
  { path: 'user/:id', component: UserProfileComponent }
    ]
  },
  { path: 'login', component: LoginComponent},
    { path: 'signup', component: SignUpComponent },
    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
