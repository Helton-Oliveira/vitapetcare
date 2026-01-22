import {Component, inject} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {TranslateModule} from '@ngx-translate/core';
import AuthService from '../../shared/services/auth/auth.service';
import {AuthActionsService} from './auth-actions-service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './auth-new-password.html',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    TranslateModule,
    NgOptimizedImage,
  ]
})
export class AuthNewPasswordComponent {
  private authService = inject(AuthService);
  private authActionsService = inject(AuthActionsService);
  private fb = inject(FormBuilder);

  isNotValid: boolean = false;

  form = this.fb.group({
    newPassword: ['', [Validators.required, Validators.minLength(6)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(6)]],
  });

  confirmResetPassword() {
    const newPasswordField = this.form.get(['newPassword'])?.value;
    const confirmPasswordField = this.form.get(['newPassword'])?.value;

    if (newPasswordField === confirmPasswordField) {
      const resetCode = window.location.search.replaceAll(/[^0-9]/g, "");
      this.authService.confirmResetPassword(newPasswordField, resetCode)
        .then(async () => {
          await this.authActionsService.goToLogin();
        })
    }

    this.isNotValid = true;
  }

  canSubmit(): boolean {
    return this.form.valid;
  }

}
