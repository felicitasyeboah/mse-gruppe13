import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ApiResponse, ApiService, User } from '../../services/api.service';
import { UserResponse } from '../../models/user-response.model';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [FormsModule, CommonModule],
})
export class LoginComponent {
  email = '';
  password = '';
  msg = '';

  constructor(
    private apiService: ApiService,
    //private toastrService: ToastrService,
    private authService: AuthService,
    private router: Router,
  ) {}

  onLogin(): void {
    this.apiService.login(this.email, this.password).subscribe({
      next: (response: UserResponse) => {
        // Weiterleitung basierend auf der Rolle
        this.handleLoginSuccess(response);
      },
      error: (error) => {
        this.handleLoginError(error);
      },
    });
  }

  private handleLoginSuccess(response: UserResponse) {
    this.msg = response.message;
    //this.toastrService.success(response.message, 'Success');
    const user: User = response.data;

    // Store user information in localStorage
    this.authService.setUser(user);

    // Determine next steps based on user role
    if (user.role.toLowerCase() === 'citizen') {
      this.handleCitizenRole(user);
    } else if (user.role.toLowerCase() === 'employee') {
      this.router.navigate(['/feedback/all-open']);
    }
  }
  private handleLoginError(error: any) {
    this.msg = error.error?.message || 'An unknown error occurred';
    //const errorMessage = error.error?.message || 'An unknown error occurred';
    // this.toastrService.error(errorMessage, 'Error');
  }
  private handleCitizenRole(user: any) {
    this.apiService.fetchUserFeedbacks(user.userId).subscribe(
      (feedbackResponse: ApiResponse) => {
        const hasFeedbacks =
          feedbackResponse.data && feedbackResponse.data.length > 0;
        const nextRoute = hasFeedbacks ? '/feedback-list' : '/feedback-form';
        this.router.navigate([nextRoute]);
      },
      (error: any) => {
        this.msg = 'Error ' + error.error?.message;
        //this.toastrService.error(error.error?.message, 'Error');
      },
    );
  }
}
