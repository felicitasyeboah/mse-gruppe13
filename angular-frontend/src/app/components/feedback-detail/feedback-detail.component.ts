import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService, FeedbackRequest } from '../../services/api.service';
import { JsonPipe, NgIf } from '@angular/common';

@Component({
  selector: 'app-feedback-detail',
  imports: [NgIf, JsonPipe],
  templateUrl: './feedback-detail.component.html',
  standalone: true,
})
export class FeedbackDetailComponent implements OnInit {
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
    private apiService: ApiService,
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
