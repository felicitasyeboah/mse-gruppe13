import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Feedback } from '../../services/api.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
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

  closeModal() {
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
