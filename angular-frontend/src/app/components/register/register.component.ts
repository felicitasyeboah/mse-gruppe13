import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ApiResponse, ApiService } from '../../services/api.service';

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  imports: [FormsModule, CommonModule],
})
export class RegisterComponent {
  username = '';
  email = '';
  password = '';
  confirmPassword = '';
  msg = '';

  constructor(
    private apiService: ApiService,
    private authService: AuthService,
    private router: Router,
  ) {}

  onRegister(): void {
    if (this.password !== this.confirmPassword) {
      this.msg = 'Passwords do not match';
      return;
    }

    this.apiService
      .register(this.username, this.email, this.password)
      .subscribe({
        next: (response: ApiResponse) => {
          this.handleRegisterSuccess(response);
        },
        error: (error) => {
          this.handleRegisterError(error);
        },
      });
  }

  private handleRegisterSuccess(response: ApiResponse) {
    this.msg = response.message;
    const user = response.data;

    // Store user information in localStorage
    this.authService.setUser(user);

    // Redirect to login page after successful registration
    this.router.navigate(['/login']);
  }

  private handleRegisterError(error: any) {
    this.msg = error.error?.message || 'An unknown error occurred';
  }
}
