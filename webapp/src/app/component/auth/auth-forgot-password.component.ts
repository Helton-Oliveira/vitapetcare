import {Component, inject, signal} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {TranslateModule} from '@ngx-translate/core';
import AuthService from '../../shared/services/auth/auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './auth-forgot-password.component.html',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    TranslateModule,
    NgOptimizedImage,
  ]
})
export class AuthForgotPasswordComponent {
  private authService = inject(AuthService);
  private fb = inject(FormBuilder);

  status = signal<'idle' | 'loading' | 'success' | 'error'>('idle');

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]]
  });

  async requestResetPassword() {
    this.status.set('loading');
    const emailField = this.form.get('email')?.value;
    const url = `${window.location.origin}/login/confirm-password`;

    try {
      const success = await this.authService.resetPasswordRequest(emailField!, url);
      this.status.set(success ? 'success' : 'error');

    } catch (err) {
      console.error('Erro na requisição:', err);
      this.status.set('error');
    }
  }

  canSubmit(): boolean {
    return this.form.valid;
  }

}
