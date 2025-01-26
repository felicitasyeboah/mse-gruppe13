import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ApiResponse, ApiService, UpdateRequest} from '../services/api.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AuthService} from '../services/auth.service';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-feedback-edit',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './feedback-edit.component.html',
  styleUrl: './feedback-edit.component.css'
})
export class FeedbackEditComponent implements OnInit {
  response: any;
  feedback: UpdateRequest = {
    comment: '',
    userId: 10,
    userRole: '',
    updateType: 'comment',
  };
  responseMessage: string | null = null; // Holds the success or error message
  isError = false; // Indicates if the message is an error
  stringId: string | null = null;
  feedbackId: number | null = null;
  employee: any | null = null;
  employeeId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    /*this.route.paramMap.subscribe(paramMap => {
      this.stringId = paramMap.get('id'); // Extrahiert den Wert von :id
      const {stringId: stringId1} = this;
      // @ts-expect-error Da ID übernommen werden soll
      this.itemId = 0 + stringId1;
      if (this.itemId == null) {
        return console.error('Der Wert für :id ist kein gültiger Wert.');
      }

    });*/
    this.route.paramMap.subscribe(params => {
      this.stringId = params.get('id'); // Extrahiert den Wert von :id
      const id = params.get('id');
      if (id) {
        this.feedbackId = +id; // String zu Zahl konvertieren
        console.log('Feedback ID:', this.feedbackId);
      }
    });
    console.log('Feedback ID:', this.feedbackId);
    this.response = this.apiService.getFeedbackById(this.stringId);
  }

  updateFeedback(): void {

    this.employee = this.authService.getUser();
    this.employeeId = this.employee.id;
    if (this.employeeId != null) {
      this.feedback.userId = this.employeeId;
    }
    this.feedback.userRole = this.authService.getUserRole()?.toUpperCase() as string;
    this.apiService.updateFeedback(this.feedbackId, this.feedback).subscribe({
      next: (response: ApiResponse) => {
        console.log('Feedback updated successfully:', response);
        this.responseMessage = response.message; // Set success message
        this.isError = false;
        this.router.navigate(['/feedback/all-open']);
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
}
