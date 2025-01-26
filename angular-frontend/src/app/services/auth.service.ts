import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  logout(): void {
    // Entfernt den Benutzer aus dem localStorage
    localStorage.removeItem('user');
  }

  // Überprüft, ob der Benutzer eingeloggt ist
  isLoggedIn(): boolean {
    return !!this.getUser();
  }

  // Gibt den aktuellen Benutzer zurück
  getUser() {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }
  setUser(user: any) {
    localStorage.setItem('user', JSON.stringify(user));
  }
  getUserRole(): string | null {
    const user = this.getUser();
    return user ? user.role.toLowerCase() : null;
  }
  clearStorage() {
    localStorage.clear();
  }
}
