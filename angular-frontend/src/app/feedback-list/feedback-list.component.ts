import { Component } from '@angular/core';
import { ApiService } from '../services/api.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-feedback-list',
  imports: [CommonModule],
  templateUrl: './feedback-list.component.html',
  styleUrl: './feedback-list.component.css',
})
export class FeedbackListComponent {
  data: any; // to store the fetched data
  errorMessage: string = ''; // to store any error message

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    // Call the service method to fetch data
    const userId = 1; // TODO: get the user id from the logged in user
    this.apiService.fetchUserFeedbacks(userId).subscribe(
      (response) => {
        this.data = response; // Handle the successful response
        console.log(this.data);
      },
      (error) => {
        this.errorMessage = error.message; // Handle the error
        console.error(error);
      },
    );
  }
}
