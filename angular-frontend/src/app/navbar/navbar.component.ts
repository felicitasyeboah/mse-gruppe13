import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {
  constructor(
    private router: Router,
    private authService: AuthService,
  ) {}
  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  getUserRole(): string | null {
    return this.authService.getUserRole();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
  hasAccess(route: string): boolean {
    const userRole = this.getUserRole();
    if (!userRole) {
      return false;
    }

    const roleAccessMap = {
      employee: ['/feedback/all-open', '/feedback/detail', '/feedback/edit'],
      citizen: ['/feedback-form', '/feedback-list'],
    };

    //@ts-expect-error TS7053
    return roleAccessMap[userRole]?.includes(route) ?? false;
  }
  navigateTo(route: string) {
    this.router.navigate([route]);
  }
}
