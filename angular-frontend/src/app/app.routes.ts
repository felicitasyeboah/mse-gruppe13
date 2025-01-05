import { Routes } from '@angular/router';
import {FeedbackDetailComponent} from './feedback-detail/feedback-detail.component';
import {ComplaintsListComponent} from './complaints-list/complaints-list.component';



export const routes: Routes = [
  { path: '', component: ComplaintsListComponent },
  { path: 'feedback/:id', component: FeedbackDetailComponent }, // Detailseite
];
//export const routes: Routes = [];
