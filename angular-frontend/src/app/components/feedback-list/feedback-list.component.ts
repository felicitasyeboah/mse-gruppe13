import { Component, OnInit } from '@angular/core';
import {
  ApiService,
  Feedback,
  FeedbackListResponse,
} from '../../services/api.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
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
  errorMessage = ''; // to store any error message

  constructor(
    private apiService: ApiService,
    private router: Router,
    private authService: AuthService,
  ) {}

  ngOnInit(): void {
    // Call the service method to fetch data
    const userId = this.authService.getUser().userId;
    this.apiService.fetchUserFeedbacks(userId).subscribe(
      (response: FeedbackListResponse) => {
        this.feedbacks = response.data;
        this.errorMessage = response.message;
        console.log('response:', response.data);
      },
      (error) => {
        this.errorMessage = error.message; // Handle the error
        console.error(error);
      },
    );
  }

  goToDetail(id: number): void {
    this.router.navigate(['/feedback/detail', id]);
  }
}
