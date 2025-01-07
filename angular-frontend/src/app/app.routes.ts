import { Routes } from '@angular/router';
import { FeedbackFormComponent } from './feedback-form/feedback-form.component';
import { FeedbackListComponent } from './feedback-list/feedback-list.component';
import { FeedbackExampleComponent } from './feedback-example/feedback-example/feedback-example.component';

export const routes: Routes = [
  { path: '', redirectTo: '/feedback-list', pathMatch: 'full' },
  { path: 'feedback-form', component: FeedbackFormComponent },
  { path: 'feedback-list', component: FeedbackListComponent },
  { path: 'feedback-example', component: FeedbackExampleComponent },
  { path: '**', redirectTo: '/feedback-list' },
];
