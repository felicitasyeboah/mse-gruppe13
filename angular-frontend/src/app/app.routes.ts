import { Routes } from '@angular/router';
import { FeedbackFormComponent } from './feedback-form/feedback-form.component';
import { FeedbackListComponent } from './feedback-list/feedback-list.component';
import { FeedbackExampleComponent } from './feedback-example/feedback-example/feedback-example.component';
import {OpenFeedbackComponent} from './open-feedback/open-feedback.component';
import {FeedbackDetailComponent} from './feedback-detail/feedback-detail.component';
import {FeedbackEditComponent} from './feedback-edit/feedback-edit.component';

export const routes: Routes = [
  { path: '', redirectTo: '/feedback-list', pathMatch: 'full' },
  { path: 'feedback-form', component: FeedbackFormComponent },
  { path: 'feedback-list', component: FeedbackListComponent },
  { path: 'feedback-example', component: FeedbackExampleComponent },
  { path: 'open-feedback', component: OpenFeedbackComponent },
  { path: 'feedback/:id', component: FeedbackDetailComponent},
  { path: 'edit-feedback/:id', component: FeedbackEditComponent},
  { path: '**', redirectTo: '/feedback-list' },
];
