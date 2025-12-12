import {Routes} from '@angular/router';

export const USER_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () =>
      import("./user.component").then(c => c.UserComponent)
  },
  {
    path: 'new',
    loadComponent: () =>
      import("./user-update.component").then(c => c.UserUpdateComponent)
  },
  {
    path: 'edit/:id',
    loadComponent: () =>
      import("./user-update.component").then(c => c.UserUpdateComponent)
  }
];


