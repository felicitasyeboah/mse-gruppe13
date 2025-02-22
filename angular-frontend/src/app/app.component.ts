import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/auth.service';
import { RouterModule, RouterOutlet } from '@angular/router';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ImpressumComponent } from './components/impressum/impressum.component';
import { CommonModule } from '@angular/common'; // Import des CommonModule

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    NavbarComponent,
    ImpressumComponent,
    RouterModule,
    RouterOutlet,
  ], // Remove the duplicate CommonModule
})
export class AppComponent implements OnInit {
  title = 'angular-frontend';

  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.authService.clearStorage();
  }
}
