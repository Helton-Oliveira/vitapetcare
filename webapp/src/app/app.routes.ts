import { Routes } from '@angular/router';

export const APP_ROUTES: Routes = [
  {
    path: "",
    loadChildren: () => import('./component/auth/auth-routes').then(r => r.LOGIN_ROUTES)
  }
];
