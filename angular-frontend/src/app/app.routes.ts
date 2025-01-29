import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { FeedbackListComponent } from './components/feedback-list/feedback-list.component';
import { OpenFeedbackComponent } from './components/open-feedback/open-feedback.component';
import { AuthGuard } from './shared/auth.guard';
import { FeedbackFormComponent } from './components/feedback-form/feedback-form.component';
import { FeedbackEditComponent } from './components/feedback-edit/feedback-edit.component';
import { RegisterComponent } from './components/register/register.component';

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
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];
