import { Component, OnInit } from '@angular/core';
import { NgForOf, NgIf } from '@angular/common';
import {
  ApiService,
  Feedback,
  FeedbackListResponse,
  User,
} from '../../services/api.service';
import { AuthService } from '../../services/auth.service';
import { FeedbackEditComponent } from '../feedback-edit/feedback-edit.component';

@Component({
  selector: 'app-open-feedback',
  imports: [NgForOf, NgIf, FeedbackEditComponent],
  templateUrl: './open-feedback.component.html',
  styleUrl: './open-feedback.component.css',
  standalone: true,
})
export class OpenFeedbackComponent implements OnInit {
  response: FeedbackListResponse | undefined; // to store the fetched data
  responseMessage: string | null = null; // Holds the success or error message
  isError = false; // Indicates if the message is an error
  isModalVisible = false;
  selectedFeedback: Feedback = { content: '' };
  constructor(
    private apiService: ApiService,
    private authService: AuthService,
  ) {}

  ngOnInit(): void {
    this.reloadOpenFeedbacks();
  }

  openModal(feedback: Feedback): void {
    this.selectedFeedback = feedback;
    this.isModalVisible = true;
  }

  handleFormSubmit(updatedFeedback: Feedback): void {
    const user = this.authService.getUser();
    console.log('User:', user);
    this.apiService
      .updateFeedback(
        updatedFeedback.id!,
        user,
        'comment',
        updatedFeedback.comment!,
      )
      .subscribe(
        (response) => {
          console.log('Feedback updated successfully:', response);
          this.isModalVisible = false;
        },
        (error) => {
          this.responseMessage =
            error?.error?.message || 'An unexpected error occurred.'; // Set error message
          console.log(error?.error?.message);
          this.isError = true;
        },
      );
  }

  closeFeedback(id: number): void {
    const user: User = this.authService.getUser();

    this.apiService.updateFeedback(id, user, 'close').subscribe(
      (response) => {
        this.responseMessage = response.message;
        this.isError = false;
        this.reloadOpenFeedbacks();
      },
      (error) => {
        this.responseMessage =
          error?.error?.message || 'An unexpected error occurred.'; // Set error message
        console.log(error?.error?.message);
        this.isError = true;
      },
    );
  }
  assign(feedbackId: number): void {
    const user: User = this.authService.getUser();
    this.apiService
      .assignFeedback(feedbackId, user.userId, user.role)
      .subscribe(
        (response) => {
          console.log(response);
          this.responseMessage = response.message;
          this.isError = false;
          this.reloadOpenFeedbacks(); // Reload the list of open feedbacks
        },
        (error) => {
          this.responseMessage =
            error?.error?.message || 'An unexpected error occurred.'; // Set error message
          console.log(error?.error?.message);
          this.isError = true;
        },
      );
  }
  private reloadOpenFeedbacks(): void {
    this.apiService.fetchOpenFeedbacks().subscribe(
      (response) => {
        this.responseMessage = response.message;
        this.isError = false;
        this.response = response; // Handle the successful response
        console.log(this.response);
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
