import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
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
}
