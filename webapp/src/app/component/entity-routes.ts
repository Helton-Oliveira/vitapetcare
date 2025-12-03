import {Routes} from '@angular/router';

export const ENTITY_ROUTES: Routes = [
  {
    path: 'login',
    loadChildren: () => import('./auth/auth-routes').then(r => r.LOGIN_ROUTES)
  },
  {
    path: 'dashboard',
    loadChildren: () => import('./dashboard/dashboard.routes').then(r => r.DASHBOARD_ROUTES)
  }
]
