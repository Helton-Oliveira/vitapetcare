import {Routes} from '@angular/router';

export const LOGIN_ROUTES: Routes = [
  {
    path: "",
    loadComponent: () => import('./auth.component').then(c => c.AuthComponent)
  }
]
