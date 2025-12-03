import {Routes} from '@angular/router';

export const APP_ROUTES: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'login'
  },
  {
    path: 'login',
    loadChildren: () =>
      import('./component/auth/auth-routes').then(r => r.LOGIN_ROUTES)
  },
  {
    path: '',
    loadComponent: () =>
      import('./setup-page-component').then(c => c.SetupPageComponent),
    loadChildren: () =>
      import('./component/entity-routes').then(r => r.ENTITY_ROUTES)
  },

  {path: '**', redirectTo: 'login'}
];
