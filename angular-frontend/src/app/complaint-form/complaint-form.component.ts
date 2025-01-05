import { Component } from '@angular/core';
import {
  ApiResponse,
  ApiService,
  FeedbackRequest,
} from '../services/api.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-complaint-form',
  imports: [FormsModule, CommonModule],
  templateUrl: './complaint-form.component.html',
  styleUrl: './complaint-form.component.css',
})
export class ComplaintFormComponent {
  feedback: FeedbackRequest = {
    title: '',
    content: '',
    citizenId: 1,
    category: '',
  };

  responseMessage: string | null = null; // Holds the success or error message
  isError: boolean = false; // Indicates if the message is an error

  constructor(private apiService: ApiService) {}

  submitFeedback() {
    this.responseMessage = null; // Reset response message before submission
    this.isError = false;

    this.apiService.createFeedback(this.feedback).subscribe({
      next: (response: ApiResponse) => {
        console.log('Feedback created successfully:', response);
        this.responseMessage = response.message; // Set success message
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
}
