import {ChangeDetectorRef, Component, inject, OnInit} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {TranslateModule, TranslatePipe, TranslateService} from '@ngx-translate/core';
import {PageService} from '../../shared/services/page/page-service';
import {ButtonBuilder} from '../../shared/builders/button-builder';
import {JobActionsService} from './JobActionsService';
import {ActionEditorModalService} from '../../shared/services/modal/action-modal-service';
import {RangePipe} from '../../shared/pipes/range-pipe';
import {TPage} from '../../shared/dto/page-dto';
import {JobService} from '../../shared/services/job/job-service';
import {Job} from '../../shared/models/job/job.model';
import {JobTypePipe} from '../../shared/pipes/job-type-pipe';
import {NgxMaskPipe} from 'ngx-mask';

@Component({
  selector: 'app-dashboard',
  templateUrl: './job.component.html',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    TranslatePipe,
    TranslateModule,
    RangePipe,
    JobTypePipe,
    NgxMaskPipe
  ]
})
export class JobComponent implements OnInit {
  private page = inject(PageService);
  private jobActionsService = inject(JobActionsService)
  private jobService = inject(JobService)
  private translateService = inject(TranslateService);
  private changeDetectorRef = inject(ChangeDetectorRef);
  private actionEditorModalService = inject(ActionEditorModalService);

  public jobs: Job[] = [];
  public pagination: TPage = {number: 0, totalPages: 0, totalElements: 0, size: 0};

  ngOnInit(): void {
    this.setup()
    this.jobService.getAll()
      .then(res => {
        this.jobs = res.content;
        this.pagination = res.page;
        this.changeDetectorRef.detectChanges();
      })
  }

  setup() {
    this.page.setPage({
      title: this.translateService.instant('job.title'),
      subtitle: this.translateService.instant('job.subTitle'),
      showSearch: true,
      actions: [
        ButtonBuilder.filter({
          title: this.translateService.instant('button.filter'),
          action: () => {
          }
        }),
        ButtonBuilder.add({
          title: this.translateService.instant('button.new'),
          action: () => this.jobActionsService.goToNew()
        }),
      ],
    });
  }

  async goEdit(id: string) {
    await this.jobActionsService.goToEdit(id)
  }

  disableToast(id?: string) {
    this.actionEditorModalService.show(() => {
      this.jobService.disable(id!!)
        .then(() => this.setup());
    }, 'md');
  }


}
