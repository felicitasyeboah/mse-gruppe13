import { Component } from '@angular/core';
import { ComplaintsListComponent } from "./complaints-list/complaints-list.component";
import { ImpressumComponent } from "./impressum/impressum.component";
import { ComplaintFormComponent } from "./complaint-form/complaint-form.component";
import { NavbarComponent } from "./navbar/navbar.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [ComplaintsListComponent, ImpressumComponent, ComplaintFormComponent, NavbarComponent]
})
export class AppComponent {
  title = 'angular-frontend';
}
