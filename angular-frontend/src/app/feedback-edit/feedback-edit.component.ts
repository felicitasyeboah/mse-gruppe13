import {Component, OnInit} from '@angular/core';
import {JsonPipe, NgIf} from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import {ApiResponse, ApiService, UpdateRequest} from '../services/api.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-feedback-edit',
  imports: [
    JsonPipe,
    NgIf,
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './feedback-edit.component.html',
  styleUrl: './feedback-edit.component.css'
})
export class FeedbackEditComponent implements OnInit {
  response: any;
  feedback: UpdateRequest = {
    comment: '',
    userId: 1,
    userRole: '',
    updateType: '',
  };
  responseMessage: string | null = null; // Holds the success or error message
  isError = false; // Indicates if the message is an error

  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService
  ) {}

  updateFeedback(): void {
    this.apiService.updateFeedback(this.feedback).subscribe({
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

  ngOnInit(): void {

    const id = this.route.snapshot.paramMap.get('id');
    this.isError = false;

    if (id) {
      this.apiService.getFeedbackById(id).subscribe((data) => {
        this.response = data;
      });
    }
  }
}
