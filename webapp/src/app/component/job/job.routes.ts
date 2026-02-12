import {Routes} from '@angular/router';

export const JOB_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () =>
      import("./job.component").then(c => c.JobComponent)
  },
  {
    path: 'new',
    loadComponent: () =>
      import("./job-update.component").then(c => c.JobUpdateComponent)
  },
  {
    path: 'edit/:id',
    loadComponent: () =>
      import("./job-update.component").then(c => c.JobUpdateComponent)
  }
];


