import {Injectable} from '@angular/core';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthActionsService {
  constructor(
    private router: Router
  ) {
  }

  async goToForgotPassword() {
    await this.router.navigate(['login', 'forgot-password']);
  }

  async goToLogin() {
    await this.router.navigate(['login']);
  }

}
