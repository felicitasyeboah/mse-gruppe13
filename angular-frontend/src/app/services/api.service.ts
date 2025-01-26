import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private readonly baseUrl = 'http://localhost:8081'; // Base API URL
  private readonly feedbackEndpoint = `${this.baseUrl}/feedback`; // Feedback endpoint URL
  private readonly userEndpoint = `${this.baseUrl}/user`; // Login endpoint URL

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
   * Fetches feedbacks for a specific user.
   * @returns An Observable of the feedbacks.
   */
  fetchOpenFeedbacks(): Observable<any> {
    const userFeedbackUrl = `${this.feedbackEndpoint}/all-open`;
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
  /**
   * Fetches a specific feedback from a specific user.
   * @param feedbackId - The ID of the user whose feedbacks are being retrieved.
   * @returns An Observable of the feedbacks.
   */
  getFeedbackById(feedbackId: string | null): Observable<any> {
    const detailFeedbackUrl = `${this.feedbackEndpoint}/${feedbackId}`;
    return this.http.get<any>(detailFeedbackUrl);
  }
  /**
   * Updates a specific feedback from a specific user.
   * @returns An Observable of the feedbacks.
   * @param feedbackId
   * @param feedback
   */
  updateFeedback(
    feedbackId: number | null,
    feedback: UpdateRequest,
  ): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const updateFeedbackUrl = `${this.feedbackEndpoint}/${feedbackId}`;
    return this.http.patch<ApiResponse>(updateFeedbackUrl, feedback, {
      headers,
    });
  }
  /**
   * Logs in a user.
   * @param email - The email of the user.
   * @param password - The password of the user.
   * @returns An Observable of the API response.
   */
  login(email: string, password: string): Observable<ApiResponse> {
    const params = new HttpParams() // Sende die Parameter in der URL statt im Body
      .set('email', email)
      .set('password', password);
    return this.http.post<ApiResponse>(
      `${this.userEndpoint}/login`,
      {},
      { params },
    );
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
 * Interface for UpdateRequest matching the server-side FeedbackRequest record.
 */
export interface UpdateRequest {
  comment: string;
  userId: number;
  userRole: string;
  updateType: string;
}

/**
 * Interface for the ApiResponse returned by the server.
 */
export interface ApiResponse {
  message: string;
  data: any;
}
