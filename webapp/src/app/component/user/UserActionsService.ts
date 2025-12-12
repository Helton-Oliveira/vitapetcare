import {Injectable} from '@angular/core';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserActionsService {
  constructor(
    private router: Router
  ) {
  }

  async goToNew() {
    await this.router.navigate(['app/user', 'new']);
  }

  async goToEdit(id: string) {
    await this.router.navigate(['app/user', 'edit', id]);
  }

  async goToList() {
    await this.router.navigate(['app', 'user']);
  }

  async goToDetail() {
    await this.router.navigate(['app/user', 'detail']);
  }

}
