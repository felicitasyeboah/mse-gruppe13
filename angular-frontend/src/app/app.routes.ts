import { Routes } from '@angular/router';
import {FeedbackDetailComponent} from './feedback-detail/feedback-detail.component';



export const routes: Routes = [
  { path: 'feedback/:id', component: FeedbackDetailComponent }, // Detailseite
];
//export const routes: Routes = [];
