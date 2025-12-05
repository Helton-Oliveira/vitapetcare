import {Routes} from '@angular/router';
import {authGuard} from './interceptor/auth-guard';
import {RedirectGuard} from './interceptor/redirect-guard';

export const APP_ROUTES: Routes = [
  {
    path: '',
    canActivate: [RedirectGuard],
    children: []
  },

  {
    path: 'login',
    loadChildren: () =>
      import('./component/auth/auth-routes').then(r => r.LOGIN_ROUTES)
  },

  {
    path: 'app',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./setup-page-component').then(c => c.SetupPageComponent),
    loadChildren: () =>
      import('./component/entity-routes').then(r => r.ENTITY_ROUTES)
  },

  {path: '**', redirectTo: 'login'}
];

