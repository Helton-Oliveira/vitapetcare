import {Injectable} from '@angular/core';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class JobActionsService {
  constructor(
    private router: Router
  ) {
  }

  async goToNew() {
    await this.router.navigate(['app/job', 'new']);
  }

  async goToEdit(id: string) {
    await this.router.navigate(['app/job', 'edit', id]);
  }

  async goToList() {
    await this.router.navigate(['app', 'job']);
  }

  async goToDetail() {
    await this.router.navigate(['app/job', 'detail']);
  }

}
