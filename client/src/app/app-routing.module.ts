import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {BookListComponent} from './book-list/book-list.component';
import {BookFormComponent} from './book-form/book-form.component';
import {UserProfileComponent} from './user-profile/user-profile.component';
import {HomeComponent} from './home/home.component';
import {LayoutComponent} from './layout/layout.component';
import {LoginComponent} from './login/login.component';
import {SignUpComponent} from './sign-up/sign-up.component';
import {AuthGuard} from './auth.guard';
import { BookSearchComponent } from './book-search/book-search.component';

// Routes are set up this way to allow header and footer for all views except login and sign up
const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {path: '', component: HomeComponent, canActivate: [AuthGuard]},
      {path: 'books', component: BookListComponent, canActivate: [AuthGuard]},
      {path: 'addBook', component: BookFormComponent, canActivate: [AuthGuard]},
      {path: 'user', component: UserProfileComponent, canActivate: [AuthGuard]},
      {path: 'user/:id', component: UserProfileComponent, canActivate: [AuthGuard]},
      {path: 'book', component: BookFormComponent, canActivate: [AuthGuard]},
      {path: 'bookSearch', component: BookSearchComponent, canActivate: [AuthGuard]}
    ]
  },
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: SignUpComponent},
  // otherwise redirect to home
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
