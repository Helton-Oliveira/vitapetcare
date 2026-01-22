import {Routes} from '@angular/router';

export const LOGIN_ROUTES: Routes = [
  {
    path: "",
    loadComponent: () => import('./auth.component').then(c => c.AuthComponent)
  },
  {
    path: 'forgot-password',
    loadComponent: () => import('./auth-forgot-password.component').then(c => c.AuthForgotPasswordComponent)
  },
  {
    path: 'confirm-password',
    loadComponent: () => import('./auth-new-password.component').then(c => c.AuthNewPasswordComponent)
  }
]
