import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-feedback-list',
  imports: [CommonModule],
  templateUrl: './feedback-list.component.html',
  styleUrl: './feedback-list.component.css',
})
export class FeedbackListComponent implements OnInit {
  data: any; // to store the fetched data
  errorMessage = ''; // to store any error message

  constructor(private apiService: ApiService, private router: Router) {}

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


  goToDetail(id: string): void {
    this.router.navigate(['/feedback', id]);
  }
}
