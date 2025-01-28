import { Component, OnInit } from '@angular/core';
import { NgForOf, NgIf } from '@angular/common';
import {
  ApiService,
  Feedback,
  FeedbackListResponse,
  User,
} from '../../services/api.service';
import { Router } from '@angular/router';
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
  feedbacks: Feedback[] | undefined; // to store the feedbacks
  errorMessage = ''; // to store any error message
  isModalVisible = false;
  selectedFeedback: Feedback = { content: '' };
  constructor(
    private apiService: ApiService,
    private router: Router,
    private authService: AuthService,
  ) {}

  ngOnInit(): void {
    this.reloadOpenFeedbacks();
  }
  goToEdit(feedback: Feedback): void {
    this.router.navigate(['/feedback', feedback]);
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
          // Update the feedback list or handle the response as needed
        },
        (error) => {
          this.errorMessage =
            error.error?.message || 'An unknown error occurred';
        },
      );
  }

  closeFeedback(id: number): void {
    const user: User = this.authService.getUser();

    this.apiService.updateFeedback(id, user, 'close').subscribe(
      (response) => {
        console.log(response);
        this.reloadOpenFeedbacks();
      },
      (error) => {
        this.errorMessage = error.error?.message || 'An unknown error occurred';
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
          this.reloadOpenFeedbacks(); // Reload the list of open feedbacks
        },
        (error) => {
          this.errorMessage =
            error.error?.message || 'An unknown error occurred';
        },
      );
  }
  private reloadOpenFeedbacks(): void {
    this.apiService.fetchOpenFeedbacks().subscribe(
      (response) => {
        this.response = response; // Handle the successful response
        console.log(this.response);
      },
      (error) => {
        this.errorMessage = error.message; // Handle the error
        console.error(error);
      },
    );
  }
}
