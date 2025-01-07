import { Routes } from '@angular/router';
import { FeedbackFormComponent } from './feedback-form/feedback-form.component';
import { FeedbackListComponent } from './feedback-list/feedback-list.component';
import { FeedbackExampleComponent } from './feedback-example/feedback-example/feedback-example.component';
import {OpenFeedbackComponent} from './open-feedback/open-feedback.component';

export const routes: Routes = [
  { path: '', redirectTo: '/feedback-list', pathMatch: 'full' },
  { path: 'feedback-form', component: FeedbackFormComponent },
  { path: 'feedback-list', component: FeedbackListComponent },
  { path: 'feedback-example', component: FeedbackExampleComponent },
  { path: 'open-feedback', component: OpenFeedbackComponent },
  { path: '**', redirectTo: '/feedback-list' },
];
