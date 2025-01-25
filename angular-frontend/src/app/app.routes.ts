import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { FeedbackListComponent } from './feedback-list/feedback-list.component';
import { OpenFeedbackComponent } from './open-feedback/open-feedback.component';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'feedback-list', component: FeedbackListComponent },
  { path: 'feedback/all-open', component: OpenFeedbackComponent },
  { path: '**', redirectTo: '' },
];
