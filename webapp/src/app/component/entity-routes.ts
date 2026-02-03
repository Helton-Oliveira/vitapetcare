import {Routes} from '@angular/router';

export const ENTITY_ROUTES: Routes = [
  {
    path: 'dashboard',
    loadChildren: () => import('./dashboard/dashboard.routes').then(r => r.DASHBOARD_ROUTES)
  },
  {
    path: 'user',
    loadChildren: () => import('./user/user.routes').then(r => r.USER_ROUTES)
  },
  {
    path: 'job',
    loadChildren: () => import('./job/job.routes').then(r => r.JOB_ROUTES)
  }
]
