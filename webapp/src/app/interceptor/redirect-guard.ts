import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';

@Injectable({providedIn: 'root'})
export class RedirectGuard implements CanActivate {

  constructor(
    private router: Router
  ) {
  }

  canActivate(): boolean {
    const isLoggedIn = localStorage.getItem('accessToken');

    if (isLoggedIn) {
      this.router.navigate(['app', 'dashboard']);
      return false;
    }

    this.router.navigate(['/login']);
    return false;
  }
}
