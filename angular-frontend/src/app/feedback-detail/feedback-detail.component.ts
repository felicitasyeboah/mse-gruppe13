import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from '../services/api.service';
import {CommonModule, NgIf} from '@angular/common';

@Component({
  selector: 'app-feedback-detail',
  imports: [
    NgIf
  ],
  templateUrl: './feedback-detail.component.html',
})
export class FeedbackDetailComponent implements OnInit {
  feedback: any;

  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.apiService.getFeedbackById(id).subscribe((data) => {
        this.feedback = data;
      });
    }
  }
}
