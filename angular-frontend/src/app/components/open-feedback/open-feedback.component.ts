import { Component, OnInit } from '@angular/core';
import { NgForOf, NgIf } from '@angular/common';
import { ApiService } from '../../services/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-open-feedback',
  imports: [NgForOf, NgIf],
  templateUrl: './open-feedback.component.html',
  styleUrl: './open-feedback.component.css',
  standalone: true,
})
export class OpenFeedbackComponent implements OnInit {
  response: any; // to store the fetched data
  errorMessage = ''; // to store any error message

  constructor(
    private apiService: ApiService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    // Call the service method to fetch data
    // TODO: If User Role = Admin, then ...
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
  goToEdit(id: string): void {
    this.router.navigate(['/feedback', id]);
  }
}
