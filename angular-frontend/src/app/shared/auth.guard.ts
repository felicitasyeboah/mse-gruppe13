import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { AuthService } from '../services/auth.service';

//import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  private readonly accessDeniedMessage =
    'You do not have permission to access this page.';

  constructor(
    private router: Router,
    //  private toastr: ToastrService,
    private authService: AuthService,
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
  ): boolean {
    const userRole = this.authService.getUserRole();

    if (!userRole) {
      this.router.navigate(['/login']);
      return false;
    }

    const requestedUrl = state.url;

    // Role-based access control
    if (this.isAccessDenied(userRole, requestedUrl)) {
      console.error(this.accessDeniedMessage, 'Access Denied');
      alert(this.accessDeniedMessage);
      //this.toastr.error(this.accessDeniedMessage, 'Access Denied');
      return false;
    }

    return true;
  }

  /**
   * Determines if access is denied based on the user's role and the requested URL.
   * @param userRole - The role of the logged-in user.
   * @param url - The URL the user is trying to access.
   * @returns true if access is denied, false otherwise.
   */
  public isAccessDenied(userRole: string, url: string): boolean {
    const roleAccessMap = {
      citizen: ['/feedback/all-open', '/feedback/detail', '/feedback/edit'],
      employee: ['/feedback-form', '/feedback-list'],
    };

    //@ts-expect-error TS7053
    return roleAccessMap[userRole]?.includes(url) ?? false;
  }
}
