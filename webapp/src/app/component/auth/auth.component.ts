import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {TranslateModule} from '@ngx-translate/core';
import {Router} from '@angular/router';
import AuthService from '../../shared/services/auth/auth.service';
import {LoginRequest} from '../../shared/models/auth/auth-dto';
import {AuthActionsService} from './auth-actions-service';

@Component({
  selector: 'app-login',
  templateUrl: './auth.component.html',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    TranslateModule,
    NgOptimizedImage,
  ]
})
export class AuthComponent implements OnInit {

  private authService = inject(AuthService);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  authActionsServices = inject(AuthActionsService);

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    rememberMe: [false],
  });

  ngOnInit(): void {
  }

  async login(): Promise<void> {
    try {
      const loginRequest: LoginRequest = this.form.value as LoginRequest;
      const response = await this.authService.login(loginRequest);

      if (response.accessToken && response.refreshToken) {
        const storage = this.form.value.rememberMe
          ? localStorage
          : sessionStorage;

        storage.setItem('accessToken', response.accessToken);
        storage.setItem('refreshToken', response.refreshToken);

        await this.router.navigate(['app', 'dashboard']);
      }

    } catch (error) {
      console.error('Erro ao autenticar', error);
    }
  }

  async goToPasswordRecovery() {
    await this.authActionsServices.goToForgotPassword();
  }

  canSubmit(): boolean {
    return this.form.valid;
  }

}
