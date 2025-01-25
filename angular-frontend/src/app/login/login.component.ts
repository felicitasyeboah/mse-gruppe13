import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { ApiResponse } from '../models/api-response.model'; // Dein Model importieren
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [FormsModule, CommonModule],
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  onLogin(): void {
    if (!this.email || !this.password) {
      this.errorMessage = 'Bitte alle Felder ausfüllen.';
      return;
    }

    this.authService.login(this.email, this.password).subscribe({
      next: (response: ApiResponse) => {
        if (response.message === 'Erfolgreich eingeloggt.') {
          this.authService.handleLoginResponse(response); // Weiterleitung basierend auf der Rolle
        }
      },
      error: (err) => {
        this.errorMessage = 'Login fehlgeschlagen. Bitte überprüfe deine Anmeldedaten.';
      }
    });
  }
}
