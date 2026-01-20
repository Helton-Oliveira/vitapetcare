import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {_, TranslateModule, TranslateService} from '@ngx-translate/core';
import {PageService} from '../../shared/services/page/page-service';
import {ButtonBuilder} from '../../shared/buiderls/button-builder';
import {User} from '../../shared/models/user/user.model';
import {UserService} from '../../shared/services/user/user-service';
import {Role} from '../../shared/models/role/role.enum';
import {UserActionsService} from './UserActionsService';
import {ActivatedRoute} from '@angular/router';
import {FileUploadService} from '../../shared/services/img/FileUploadService';
import {FileApp, IFileApp} from '../../shared/models/file/file-app-model';
import {FileType} from '../../shared/models/file/file-type';
import {NgxDropzoneModule} from 'ngx-dropzone';
import {WorkDayEditorModalService} from './workDayModal/work-day-editor-modal-service';
import {WorkDay} from '../../shared/models/workDay/work-day-model';
import {
  MatAccordion,
  MatExpansionPanel,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from '@angular/material/expansion';

@Component({
  selector: 'app-dashboard',
  templateUrl: './user-update.component.html',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    TranslateModule,
    NgxDropzoneModule,
    MatAccordion,
    MatExpansionPanel,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle,
    NgOptimizedImage,
  ]
})
export class UserUpdateComponent implements OnInit {
  private page = inject(PageService);
  private userActionsService = inject(UserActionsService);
  private userService = inject(UserService);
  private translateService = inject(TranslateService);
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private fileUploadService = inject(FileUploadService);
  private workDayEditorModalService = inject(WorkDayEditorModalService);

  user: User = new User();
  roles: Role[] = Object.values(Role);
  fileApp: IFileApp = new FileApp();
  files: File[] = [];

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
        .then((res) => {
          this.user = res;
          this.form.get('password')?.disable();
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

  async addImage(event: any): Promise<void> {
    if (event.addedFiles.length > 0) {
      this.files = [event.addedFiles[0]];

      try {
        const fileToUpload = this.files[0];
        const urlFile = await this.fileUploadService.uploadImage(fileToUpload);

        this.fileApp = {
          name: fileToUpload.name,
          path: urlFile.url,
          type: FileType.IMAGE,
          edited: true,
          active: true,
        };
        this.markFormAsChanged();
      } catch (error) {
        this.files = [];
      }
    }
  }

  deleteImage($event?: any, file?: FileApp): void {
    $event?.stopPropagation();
    this.user.files?.filter(f => f.uuid === file?.uuid)
      .forEach(file => {
        file.edited = true
        file.active = false
        this.markFormAsChanged();
      });
  }

  addWorkDay($event?: any) {
    $event?.stopPropagation();
    this.workDayEditorModalService.show(
      this.user, undefined,
      (workDay) => {
        this.user.workDays = [...(this.user.workDays || []), workDay];
        this.markFormAsChanged();
      }
    );
  }

  editWorkDay(workDay: WorkDay) {
    this.workDayEditorModalService.show(
      this.user, workDay,
      (updatedWorkDay) => {
        this.user.workDays = this.user.workDays?.map(day =>
          day.uuid === updatedWorkDay.uuid ? updatedWorkDay : day
        );
        this.markFormAsChanged();
      }
    )
  }

  deleteWorkDay(uuid: string): void {
    this.user.workDays?.filter(wk => wk.uuid === uuid)
      .forEach(wk => {
        wk.active = false
        wk.edited = true
      });
    this.markFormAsChanged();
  }

  private updateUser() {
    this.user = {
      ...this.user,
      name: this.form.value.name,
      email: this.form.value.email,
      password: this.form.value.password,
      role: this.form.value.role,
      files: [this.fileApp],
      workDays: this.user.workDays,
      edited: true,
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
    const canId = this.user?.id != null;
    switch (canId) {
      case false:
        this.updateUser();
        await this.userService.save(this.user);
        break;
      case true:
        this.updateUser();
        await this.userService.update(this.user);
        break;
    }
    await this.userActionsService.goToList();
  }

  canSubmit(): boolean {
    return this.form.valid
      && this.form.dirty
  }

  private markFormAsChanged(): void {
    this.form.markAsDirty();
    this.form.updateValueAndValidity({emitEvent: false});
    this.setup();
  }

  getWorkDaysActive(): WorkDay[] | undefined {
    return this.user.workDays?.filter(wk => wk.active);
  }
}
