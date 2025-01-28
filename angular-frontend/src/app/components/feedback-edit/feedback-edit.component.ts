import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService, Feedback } from '../../services/api.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { CommonModule, NgIf } from '@angular/common';

@Component({
  selector: 'app-feedback-edit',
  imports: [FormsModule, ReactiveFormsModule, CommonModule, NgIf],
  templateUrl: './feedback-edit.component.html',
  styleUrl: './feedback-edit.component.css',
})
export class FeedbackEditComponent {
  comment = '';
  @Input() isVisible = false;
  @Input() feedback: Feedback | undefined;
  @Output() formSubmit = new EventEmitter<Feedback>();
  // response: any;
  // feedbackRequest: UpdateRequest = {
  //   comment: '',
  //   userId: 5,
  //   userRole: 'EMPLOYEE',
  //   updateType: 'comment',
  // };
  // responseMessage: string | null = null; // Holds the success or error message
  // isError = false; // Indicates if the message is an error
  // feedback: Feedback | undefined;

  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService,
    private router: Router,
    private authService: AuthService,
  ) {}

  // updateFeedback(): void {
  //   const user = this.authService.getUser();
  //   this.apiService.updateFeedback(this.feedback?.id!, user).subscribe({
  //     next: (response: FeedbackResponse) => {
  //       console.log('Feedback updated successfully:', response);
  //       this.responseMessage = response.message; // Set success message
  //       this.isError = false;
  //       this.router.navigate(['/all-open']);
  //     },
  //     error: (error) => {
  //       console.error('Error updating feedback:', error);
  //       this.responseMessage =
  //         error?.error?.message || 'An unexpected error occurred.'; // Set error message
  //       console.log(error?.error?.message);
  //       this.isError = true;
  //     },
  //   });
  // }

  // ngOnInit(): void {
  //   this.route.data.subscribe((data) => {
  //     console.log('data', data);
  //     //this.feedback = data;
  //   });
  //   // this.route.paramMap.subscribe((paramMap) => {
  //   //   this.feedback = paramMap.get('feedback')!; // Extrahiert den Wert von :id
  //   // });
  // }

  closeModal() {
    //this.feedback = undefined;
    this.isVisible = false;
  }

  onSubmit() {
    if (this.comment) {
      this.feedback!.comment = this.comment;
      this.formSubmit.emit(this.feedback);
      this.closeModal();
    }
  }
}
