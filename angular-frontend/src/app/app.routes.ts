import { Routes } from '@angular/router';
import { FeedbackFormComponent } from './feedback-form/feedback-form.component';
import { FeedbackListComponent } from './feedback-list/feedback-list.component';
import { FeedbackExampleComponent } from './feedback-example/feedback-example/feedback-example.component';
import { ImpressumComponent } from './impressum/impressum.component';
import { LoginComponent } from './login/login.component';

export const routes: Routes = [
  { path: '', redirectTo: '/feedback-list', pathMatch: 'full' },
  { path: 'feedback-form', component: FeedbackFormComponent },
  { path: 'feedback-list', component: FeedbackListComponent },
  { path: 'feedback-example', component: FeedbackExampleComponent },
  { path: 'impressum', component: ImpressumComponent },
  { path: 'login', component: LoginComponent }, // Login-Route vor '**' platzieren
  { path: '**', redirectTo: '/feedback-list' } // Wildcard-Route immer zuletzt
];
