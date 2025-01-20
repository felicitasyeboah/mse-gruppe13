import {Component, OnInit} from '@angular/core';
import {JsonPipe, NgIf} from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import {ApiService, FeedbackRequest} from '../services/api.service';

@Component({
  selector: 'app-feedback-edit',
  imports: [
    JsonPipe,
    NgIf
  ],
  templateUrl: './feedback-edit.component.html',
  styleUrl: './feedback-edit.component.css'
})
export class FeedbackEditComponent implements OnInit {
  response: any;
  feedback: FeedbackRequest = {
    title: '',
    content: '',
    citizenId: 1,
    category: '',
  };
  responseMessage: string | null = null; // Holds the success or error message

  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {

    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.apiService.getFeedbackById(id).subscribe((data) => {
        this.response = data;
      });
    }
  }
}
