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

  navigateTo(route: string) {
    //const userRole = this.getUserRole();

    // if (userRole === 'citizen' && route === '/feedback-example') {
    //   this.toastr.error(
    //     'You do not have permission to access this page.',
    //     'Access Denied',
    //   );
    //   return;
    // } else if (
    //   userRole === 'employee' &&
    //   (route === '/feedback-form' || route === '/feedback-list')
    // ) {
    //   this.toastr.error(
    //     'You do not have permission to access this page.',
    //     'Access Denied',
    //   );
    //   return;
    // }

    this.router.navigate([route]);
  }
}
