import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ApiResponse, ApiService, UpdateRequest} from '../services/api.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-feedback-edit',
  imports: [
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
    userId: 5,
    userRole: 'EMPLOYEE',
    updateType: 'comment',
  };
  responseMessage: string | null = null; // Holds the success or error message
  isError = false; // Indicates if the message is an error
  stringId: string | null = null;
  itemId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService,
    private router: Router
  ) {}

  updateFeedback(): void {
    this.apiService.updateFeedback(this.itemId, this.feedback).subscribe({
      next: (response: ApiResponse) => {
        console.log('Feedback updated successfully:', response);
        this.responseMessage = response.message; // Set success message
        this.isError = false;
        this.router.navigate(['/all-open']);
      },
      error: (error) => {
        console.error('Error updating feedback:', error);
        this.responseMessage =
          error?.error?.message || 'An unexpected error occurred.'; // Set error message
        console.log(error?.error?.message);
        this.isError = true;
      },
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(paramMap => {
      this.stringId = paramMap.get('id'); // Extrahiert den Wert von :id
      const {stringId: stringId1} = this;
      // @ts-ignore
      this.itemId = +stringId1;
      if (this.itemId == null) {
        return console.error('Der Wert für :id ist kein gültiger Wert.');
      }

    });
    this.response = this.apiService.getFeedbackById(this.stringId);
  }
}
