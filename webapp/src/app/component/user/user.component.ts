import {ChangeDetectorRef, Component, inject, OnInit} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {TranslateModule, TranslatePipe, TranslateService} from '@ngx-translate/core';
import {PageService} from '../../shared/services/page/page-service';
import {ButtonBuilder} from '../../shared/builders/button-builder';
import {User} from '../../shared/models/user/user.model';
import {UserService} from '../../shared/services/user/user-service';
import {UserActionsService} from './UserActionsService';
import {RolePipe} from '../../shared/pipes/role-pipe';
import {ActionEditorModalService} from '../../shared/services/modal/action-modal-service';
import {RangePipe} from '../../shared/pipes/range-pipe';
import {TPage} from '../../shared/dto/page-dto';

@Component({
  selector: 'app-dashboard',
  templateUrl: './user.component.html',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    RolePipe,
    TranslatePipe,
    TranslateModule,
    RangePipe
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
  public pagination: TPage = {number: 0, totalPages: 0, totalElements: 0, size: 0};

  ngOnInit(): void {
    this.setup()
    this.userService.getAll()
      .then(res => {
        this.users = res.content;
        this.pagination = res.page;
        this.changeDetectorRef.detectChanges();
      })
  }

  setup() {
    this.page.setPage({
      title: this.translateService.instant('user.title'),
      subtitle: this.translateService.instant('user.subTitle'),
      showSearch: true,
      actions: [
        ButtonBuilder.filter({
          title: this.translateService.instant('button.filter'),
          action: () => {
          }
        }),
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
    this.actionEditorModalService.show(() => {
      this.userService.disable(id!!)
        .then(() => this.setup());
    }, 'md');
  }


}
