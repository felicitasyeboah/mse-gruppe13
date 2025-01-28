import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserResponse } from '../models/user-response.model';

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
  fetchOpenFeedbacks(): Observable<FeedbackListResponse> {
    const userFeedbackUrl = `${this.feedbackEndpoint}/all-open`;
    return this.http.get<any>(userFeedbackUrl);
  }
  /**
   * Creates a new feedback.
   * @param feedback - The feedback request object.
   * @returns An Observable of the API response.
   */
  createFeedback(feedback: FeedbackRequest): Observable<FeedbackResponse> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<FeedbackResponse>(this.feedbackEndpoint, feedback, {
      headers,
    });
  }
  /**
   * Fetches a specific feedback from a specific user.
   * @param feedbackId - The ID of the user whose feedbacks are being retrieved.
   * @returns An Observable of the feedbacks.
   */
  getFeedbackById(feedbackId: string | null): Observable<FeedbackResponse> {
    const detailFeedbackUrl = `${this.feedbackEndpoint}/${feedbackId}`;
    return this.http.get<any>(detailFeedbackUrl);
  }
  /**
   * Updates a specific feedback from a specific user.
   * @returns An Observable of the feedbacks.
   * @param feedbackId
   * @param employee
   * @param updateType
   * @param comment
   */
  updateFeedback(
    feedbackId: number,
    employee: User,
    updateType: string,
    comment?: string,
  ): Observable<FeedbackResponse> {
    const request: UpdateRequest = {
      comment: comment,
      userId: employee.userId,
      userRole: employee.role,
      updateType: updateType,
    };
    return this.http.patch<FeedbackResponse>(
      `${this.feedbackEndpoint}/${feedbackId}`,
      request,
    );
  }
  assignFeedback(
    feedbackId: number,
    userId: number,
    userRole: string,
  ): Observable<FeedbackResponse> {
    const request: UpdateRequest = {
      userId: userId,
      userRole: userRole,
      updateType: 'assign',
    };
    return this.http.patch<FeedbackResponse>(
      `${this.feedbackEndpoint}/${feedbackId}`,
      request,
    );
  }
  /**
   * Logs in a user.
   * @param email - The email of the user.
   * @param password - The password of the user.
   * @returns An Observable of the API response.
   */
  login(email: string, password: string): Observable<UserResponse> {
    const params = new HttpParams() // Sende die Parameter in der URL statt im Body
      .set('email', email)
      .set('password', password);
    return this.http.post<UserResponse>(
      `${this.userEndpoint}/login`,
      {},
      { params },
    );
  }
  register(
    username: string,
    email: string,
    password: string,
  ): Observable<UserResponse> {
    const params = new HttpParams()
      .set('userName', username) // Sende die Parameter in der URL statt im Body
      .set('email', email)
      .set('password', password);
    const url = `${this.userEndpoint}/register`;
    return this.http.post<UserResponse>(url, {}, { params });
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
  comment?: string;
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

export interface FeedbackListResponse {
  message: string;
  data: Feedback[];
}

export interface FeedbackResponse {
  message: string;
  data: Feedback;
}

export interface Feedback {
  id?: number;
  title?: string;
  content?: string;
  category?: string;
  citizenId?: number;
  citizenName?: string;
  citizenEmail?: string;
  employeeId?: number;
  employeeName?: string;
  employeeEmail?: string;
  comment?: string;
  status?: string;
  createdAt?: string; // ISO 8601 formatted date-time string
  updatedAt?: string; // ISO 8601 formatted date-time string
}

export interface User {
  userId: number;
  userName: string;
  email: string;
  role: string;
}
