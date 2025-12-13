import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {_, TranslateModule, TranslateService} from '@ngx-translate/core';
import {PageService} from '../../shared/services/page/page-service';
import {ButtonBuilder} from '../../shared/buiderls/button-builder';
import {User} from '../../shared/models/user/user.model';
import {UserService} from '../../shared/services/user/user-service';
import {Role} from '../../shared/models/user/role';
import {UserActionsService} from './UserActionsService';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './user-update.component.html',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    TranslateModule,
  ]
})
export class UserUpdateComponent implements OnInit {
  private page = inject(PageService);
  private userActionsService = inject(UserActionsService)
  private userService = inject(UserService)
  private translateService = inject(TranslateService);
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);

  user: User = new User();
  roles: Role[] = Object.values(Role);

  form = this.fb.group({
    name: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    role: ['', Validators.required],
  });

  ngOnInit(): void {
    this.setup()
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.userService.get(id)
        .then(res => {
          this.user = res;
          this.updateForm();
        })
    }
    this.form.valueChanges.subscribe(() => this.setup());
  }

  setup() {
    this.page.setPage({
      title: this.translateService.instant(_('user.singleTitle')),
      subtitle: this.translateService.instant(_('global.field.new')),
      showSearch: true,
      actions: [
        ButtonBuilder.saveButton({
          title: this.translateService.instant(_('button.save')),
          isActive: this.canSubmit(),
          action: () => this.onSave()
        }),
        ButtonBuilder.cancel({
          title: this.translateService.instant(_('button.cancel')),
          action: () => this.userActionsService.goToList()
        }),
      ],
    });
  }

  private updateUser() {
    this.user = {
      name: this.form.value.name,
      email: this.form.value.email,
      password: this.form.value.password,
      role: this.form.value.role,
      _edited: true,
      active: true
    } as User;
  }

  private updateForm() {
    this.form.patchValue({
      name: this.user.name,
      email: this.user.email,
      role: this.user.role
    });
  }

  async onSave() {
    switch (!!this.user?.id) {
      case false:
        this.updateUser();
        await this.userService.save(this.user);
        break;
      case true:
        await this.userService.update(this.user);
        break;
    }
  }

  canSubmit(): boolean {
    return this.form.valid
      && this.form.dirty
  }

}
