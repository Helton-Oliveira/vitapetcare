import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';

@Injectable({providedIn: 'root'})
export class RedirectGuard implements CanActivate {

  constructor(
    private router: Router
  ) {
  }

  async canActivate(): Promise<boolean> {
    const isLoggedIn = localStorage.getItem('accessToken') || sessionStorage.getItem('accessToken');

    if (isLoggedIn) {
      await this.router.navigate(['app', 'dashboard']);
      return false;
    }

    await this.router.navigate(['/login']);
    return false;
  }
}
