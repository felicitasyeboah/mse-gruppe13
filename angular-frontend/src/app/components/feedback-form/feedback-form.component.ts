import { Component, OnInit } from '@angular/core';
import {
  ApiResponse,
  ApiService,
  FeedbackRequest,
} from '../../services/api.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-feedback-form',
  imports: [FormsModule, CommonModule],
  standalone: true,
  templateUrl: './feedback-form.component.html',
  styleUrl: './feedback-form.component.css',
})
export class FeedbackFormComponent implements OnInit {
  feedback: FeedbackRequest = {} as FeedbackRequest; // Feedback object to be submitted

  responseMessage: string | null = null; // Holds the success or error message
  isError = false; // Indicates if the message is an error

  constructor(
    private apiService: ApiService,
    private authService: AuthService,
  ) {}

  ngOnInit(): void {
    this.resetForm();
  }
  submitFeedback() {
    this.responseMessage = null; // Reset response message before submission
    this.isError = false;

    this.apiService.createFeedback(this.feedback).subscribe({
      next: (response: ApiResponse) => {
        console.log('Feedback created successfully:', response);
        this.responseMessage = response.message; // Set success message
        this.resetForm();
        this.isError = false;
      },
      error: (error) => {
        console.error('Error creating feedback:', error);
        this.responseMessage =
          error?.error?.message || 'An unexpected error occurred.'; // Set error message
        console.log(error?.error?.message);
        this.isError = true;
      },
    });
  }
  resetForm() {
    this.feedback = {
      title: '',
      content: '',
      citizenId: this.authService.getUser().userId,
      category: '',
    };
  }
}
