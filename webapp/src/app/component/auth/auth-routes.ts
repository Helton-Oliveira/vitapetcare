import { Routes } from '@angular/router';
import {AuthComponent} from './auth.component';

export const LOGIN_ROUTES: Routes = [
  {
    path: "",
    loadComponent: () => import('./auth.component').then(c => c.AuthComponent)
  }
]
