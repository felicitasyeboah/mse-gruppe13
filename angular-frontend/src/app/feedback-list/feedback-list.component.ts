import { ApiService } from '../services/api.service';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-feedback-list',
  imports: [CommonModule],
  templateUrl: './feedback-list.component.html',
  styleUrl: './feedback-list.component.css',
  standalone: true // Deklariert die Komponente als Standalone
})
export class FeedbackListComponent implements OnInit {
  data: any; // to store the fetched data
  errorMessage: string = ''; // to store any error message

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    // Call the service method to fetch data
    const userId = 1; // TODO: get the user id from the logged in user
    this.apiService.fetchUserFeedbacks(userId).subscribe(
      (response: any) => {
        this.data = response; // Handle the successful response
        console.log(this.data);
      },
      (error: { message: string; }) => {
        this.errorMessage = error.message; // Handle the error
        console.error(error);
      },
    );
  }
}
