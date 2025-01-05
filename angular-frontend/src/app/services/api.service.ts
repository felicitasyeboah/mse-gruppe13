import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private readonly baseUrl = 'http://localhost:8081'; // Base API URL
  private readonly feedbackEndpoint = `${this.baseUrl}/feedback`; // Feedback endpoint URL

  constructor(private http: HttpClient) {}

  /**
   * Fetches feedbacks for a specific user.
   * @param userId - The ID of the user whose feedbacks are being retrieved.
   * @returns An Observable of the feedbacks.
   */
  fetchUserFeedbacks(userId: number): Observable<any> {
    const userFeedbackUrl = `${this.feedbackEndpoint}/user/${userId}`;
    return this.http.get<any>(userFeedbackUrl);
  }
  /**
   * Creates a new feedback.
   * @param feedback - The feedback request object.
   * @returns An Observable of the API response.
   */
  createFeedback(feedback: FeedbackRequest): Observable<ApiResponse> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<ApiResponse>(this.feedbackEndpoint, feedback, {
      headers,
    });
  }
}

/**
 * Interface for FeedbackRequest matching the server-side FeedbackRequest record.
 */
export interface FeedbackRequest {
  title: string;
  content: string;
  citizenId: number;
  category: string;
}

/**
 * Interface for the ApiResponse returned by the server.
 */
export interface ApiResponse {
  message: string;
  data: any;
}
