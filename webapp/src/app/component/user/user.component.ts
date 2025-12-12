import {ChangeDetectorRef, Component, inject, OnInit} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
import {PageService} from '../../shared/services/page/page-service';
import {ButtonBuilder} from '../../shared/buiderls/button-builder';
import {User} from '../../shared/models/user/user.model';
import {UserService} from '../../shared/services/user/user-service';
import {UserActionsService} from './UserActionsService';
import {RolePipe} from '../../shared/pipes/role-pipe';
import {ActionEditorModalService} from '../../shared/services/modal/action-modal-service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './user.component.html',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    RolePipe,
    TranslatePipe,
  ]
})
export class UserComponent implements OnInit {
  private page = inject(PageService);
  private userActionsService = inject(UserActionsService)
  private userService = inject(UserService)
  private translateService = inject(TranslateService);
  private changeDetectorRef = inject(ChangeDetectorRef);
  private actionEditorModalService = inject(ActionEditorModalService);

  public users: User[] = [];

  ngOnInit(): void {
    this.setup()
    this.userService.getAll()
      .then(res => {
        this.users = res.content;
        this.changeDetectorRef.detectChanges();
      })
  }

  setup() {
    this.page.setPage({
      title: this.translateService.instant('user.title'),
      subtitle: this.translateService.instant('user.subTitle'),
      showSearch: true,
      actions: [
        ButtonBuilder.sort({}),
        ButtonBuilder.filter({}),
        ButtonBuilder.add({
          title: this.translateService.instant('button.new'),
          action: () => this.userActionsService.goToNew()
        }),
      ],
    });
  }

  async goEdit(id: string) {
    await this.userActionsService.goToEdit(id)
  }

  disableToast(id?: string) {
    this.actionEditorModalService.show(() => this.userService.disable(id!!), 'md');
  }


}
