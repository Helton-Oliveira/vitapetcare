import {Component, Input} from '@angular/core';
import {TranslateModule} from '@ngx-translate/core';
import {RouterLink} from '@angular/router';
import {User} from '../../shared/models/user/user.model';
import {Role} from '../../shared/models/role/role.enum';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  imports: [
    TranslateModule,
    RouterLink,
  ]
})
export class SidebarComponent {
  private _currentAccount: User | undefined;

  @Input()
  set currentAccount(value: User | undefined) {
    this._currentAccount = value;
  }

  get currentAccount(): User | undefined {
    return this._currentAccount;
  }

  hasPermission(permission: string): boolean {
    if (!this.currentAccount?.authorities) return false;
    return this.currentAccount.authorities.includes(permission);
  }

  isAdmin(): boolean {
    return this.currentAccount?.role === Role.ADMIN;
  }

}
