import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {_, TranslateModule, TranslateService} from '@ngx-translate/core';
import {PageService} from '../../shared/services/page/page-service';
import {ButtonBuilder} from '../../shared/builders/button-builder';
import {JobActionsService} from './JobActionsService';
import {ActivatedRoute} from '@angular/router';
import {JobService} from '../../shared/services/job/job-service';
import {Job} from '../../shared/models/job/job.model';
import {Role} from '../../shared/models/role/role.enum';

import {Status} from '../../shared/enumerations/Status';
import {JobType} from '../../shared/models/job/JobType';
import {NgxMaskDirective} from 'ngx-mask';
import {JobTypePipe} from '../../shared/pipes/job-type-pipe';

@Component({
  selector: 'app-dashboard',
  templateUrl: './job-update.component.html',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    TranslateModule,
    NgxMaskDirective,
    JobTypePipe
  ]
})
export class JobUpdateComponent implements OnInit {
  private page = inject(PageService);
  private jobActionsService = inject(JobActionsService);
  private jobService = inject(JobService);
  private translateService = inject(TranslateService);
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);

  job: Job = new Job();
  typesOfProfessionals: Role[] = Job.getTypesOfProfessionals();
  jobTypes = Object.values(JobType).filter(value => typeof value === 'string');
  statuses = Object.values(Status).filter(value => typeof value === 'string');

  form = this.fb.group({
    name: ['', Validators.required],
    duration: [null as number | null, [Validators.required]],
    valueInCents: [null as number | null, [Validators.required]],
    typesOfProfessionals: ['', [Validators.required]],
    type: ['', [Validators.required]],
    hasReturn: [false],
    timeToReturnInDays: [null as number | null],
    status: ['', Validators.required],
  });

  ngOnInit(): void {
    this.setup()
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.jobService.get(id)
        .then((res) => {
          this.job = res;
          this.updateForm();
        })
    }
    this.form.valueChanges.subscribe(() => this.setup());
  }

  setup() {
    this.page.setPage({
      title: this.translateService.instant(_('job.singleTitle')),
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
          action: () => this.jobActionsService.goToList()
        }),
      ],
    });
  }

  private updateJob() {
    this.job = {
      ...this.job,
      name: this.form.value.name,
      valueInCents: this.form.value.valueInCents,
      status: this.form.value.status,
      typesOfProfessionals: this.form.value.typesOfProfessionals,
      duration: this.form.value.duration,
      type: this.form.value.type,
      hasReturn: this.form.value.hasReturn,
      timeToReturnInDays: this.form.value.timeToReturnInDays,
      edited: true,
      active: true
    } as Job;
  }

  private updateForm() {
    this.form.patchValue({
      name: this.job.name,
      duration: this.job.duration,
      valueInCents: this.job.valueInCents,
      typesOfProfessionals: this.job.typesOfProfessionals,
      type: this.job.type?.toString(),
      hasReturn: this.job.hasReturn,
      timeToReturnInDays: this.job.timeToReturnInDays,
      status: this.job.status?.toString()
    });
  }

  async onSave() {
    const canId = this.job?.id != null;
    switch (canId) {
      case false:
        this.updateJob();
        await this.jobService.save(this.job);
        break;
      case true:
        this.updateJob();
        await this.jobService.update(this.job);
        break;
    }
    await this.jobActionsService.goToList();
  }

  canSubmit(): boolean {
    return this.form.valid
      && this.form.dirty
  }
}
