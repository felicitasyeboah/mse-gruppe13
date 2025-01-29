import { Component, OnInit } from '@angular/core';
import {
  ApiService,
  Feedback,
  FeedbackListResponse,
} from '../../services/api.service';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-feedback-list',
  imports: [CommonModule],
  standalone: true,
  templateUrl: './feedback-list.component.html',
  styleUrl: './feedback-list.component.css',
})
export class FeedbackListComponent implements OnInit {
  response: FeedbackListResponse | undefined;
  feedbacks: Feedback[] | undefined;
  responseMessage: string | null = null; // Holds the success or error message
  isError = false; // Indicates if the message is an error
  constructor(
    private readonly apiService: ApiService,
    private readonly authService: AuthService,
  ) {}

  ngOnInit(): void {
    // Call the service method to fetch data
    const userId = this.authService.getUser().userId;
    this.apiService.fetchUserFeedbacks(userId).subscribe(
      (response: FeedbackListResponse) => {
        this.feedbacks = response.data;
        this.responseMessage = response.message;
        this.isError = false;
      },
      (error) => {
        this.responseMessage =
          error?.error?.message || 'An unexpected error occurred.'; // Set error message
        console.log(error?.error?.message);
        this.isError = true;
      },
    );
  }
}
