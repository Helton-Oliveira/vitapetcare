import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {TranslateModule} from '@ngx-translate/core';
import {Router} from '@angular/router';
import AuthService from '../../shared/services/auth/auth.service';
import {LoginRequest} from '../../shared/models/auth/auth-dto';

@Component({
  selector: 'app-login',
  templateUrl: './auth.component.html',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    TranslateModule,
  ]
})
export class AuthComponent implements OnInit {

  private authService = inject(AuthService);
  private router = inject(Router);
  private fb = inject(FormBuilder);

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });

  ngOnInit(): void {
  }

  login(): void {
    const loginRequest: LoginRequest = this.form.value as LoginRequest;

    this.authService.login(loginRequest).then(async (res) => {
      if (res.accessToken != null && res.refreshToken != null) {
        localStorage.setItem('accessToken', res.accessToken)
        localStorage.setItem('refreshToken', res.refreshToken)
      }
      await this.router.navigate(['app', 'dashboard'])
    }).catch(e => console.warn(e.message))
  }

  canSubmit(): boolean {
    return this.form.valid && this.form.dirty;
  }

}
