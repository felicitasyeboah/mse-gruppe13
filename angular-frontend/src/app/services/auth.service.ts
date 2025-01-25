import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/api-response.model';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';  // Importiere tap

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly loginUrl = 'http://localhost:8081/user/login'; // Login-URL des Backends

  constructor(private http: HttpClient, private router: Router) {}

  login(email: string, password: string): Observable<ApiResponse> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const params = { email, password }; // Sende die Parameter in der URL statt im Body
    return this.http.post<ApiResponse>(this.loginUrl, null, { headers, params })
      .pipe(
        tap((response: ApiResponse) => this.handleLoginResponse(response))
      );
  }
  

  handleLoginResponse(response: ApiResponse): void {
    const user = response.data;
    if (user) {
      // Speichert den Benutzer im localStorage
      localStorage.setItem('user', JSON.stringify(user));

      // Weiterleitung basierend auf der Rolle des Benutzers
      if (user.role === 'CITIZEN') {
        this.router.navigate(['feedback-list']);
      } else if (user.role === 'EMPLOYEE') {
        this.router.navigate(['feedback/all-open']);
      }
    }
  }

  logout(): void {
    // Entfernt den Benutzer aus dem localStorage
    localStorage.removeItem('user');
    
    // Leitet zur Login-Seite weiter
    this.router.navigate(['']);
  }

  // Überprüft, ob der Benutzer eingeloggt ist
  isLoggedIn(): boolean {
    const user = localStorage.getItem('user');
    return user !== null;
  }

  // Gibt den aktuellen Benutzer zurück
  getCurrentUser(): any {
    return JSON.parse(localStorage.getItem('user') || '{}');
  }

  // Gibt den Status des Benutzers zurück, ob er eingeloggt ist oder nicht
  getUserStatus(): boolean {
    return this.isLoggedIn();
  }
}
