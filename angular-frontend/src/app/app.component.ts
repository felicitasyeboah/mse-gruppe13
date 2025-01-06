import { Component } from '@angular/core';
import { ImpressumComponent } from './impressum/impressum.component';
import { NavbarComponent } from './navbar/navbar.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [NavbarComponent, ImpressumComponent, RouterOutlet],
})
export class AppComponent {
  title = 'angular-frontend';
}
