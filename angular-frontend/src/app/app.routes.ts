import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { FeedbackListComponent } from './feedback-list/feedback-list.component';
import { OpenFeedbackComponent } from './open-feedback/open-feedback.component';
import { AuthGuard } from './shared/auth.guard';
import { FeedbackFormComponent } from './feedback-form/feedback-form.component';
import { FeedbackEditComponent } from './feedback-edit/feedback-edit.component';
import { FeedbackDetailComponent } from './feedback-detail/feedback-detail.component';
import { RegisterComponent } from './register/register.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'feedback-form',
    component: FeedbackFormComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'feedback-list',
    component: FeedbackListComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'feedback/all-open',
    component: OpenFeedbackComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'feedback/:id',
    component: FeedbackEditComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'feedback/detail/:id',
    component: FeedbackDetailComponent,
    canActivate: [AuthGuard],
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];
