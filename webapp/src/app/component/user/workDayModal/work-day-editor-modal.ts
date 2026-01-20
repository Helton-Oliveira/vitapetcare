import {Component, inject, Input, OnInit} from '@angular/core';
import {FormArray, FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {User} from '../../../shared/models/user/user.model';
import {WorkDay} from '../../../shared/models/workDay/work-day-model';
import {TranslateModule} from '@ngx-translate/core';
import {DayOfWeek} from '../../../shared/models/workDay/day-of-week.enum';
import {ModalRef} from '../../../shared/services/modal/ModalRef';
import {TimePeriod} from '../../../shared/models/timePeriod/time-period.model';
import {NgClass} from '@angular/common';
import {StringUtils} from '../../../shared/utils/string-utils';

@Component({
  selector: 'app-work-day-editor-modal',
  templateUrl: 'work-day-editor-modal.html',
  imports: [
    TranslateModule,
    ReactiveFormsModule,
    NgClass
  ]
})
export class WorkDayEditorModal implements OnInit {
  private fb = inject(FormBuilder);
  modalRef!: ModalRef;

  @Input()
  user?: User;

  @Input()
  workDay?: WorkDay;

  daysOFWeek?: DayOfWeek[] = WorkDay.getAllDays();
  timePeriods?: TimePeriod[] = [];

  form = this.fb.group({
    dayOfWeek: ['', Validators.required],
    shifts: this.fb.array([]),
  });

  shifts: FormArray = this.form.get('shifts') as FormArray;

  ngOnInit(): void {
    const dayOfWeekField = this.form.get(['dayOfWeek'])!;
    dayOfWeekField.valueChanges.subscribe((value) => {
      if (this.shifts.length === 0 && value) {
        this.addShift();
      }
    });

    if (this.workDay?.uuid) {
      this.updateForm();
    }
  }

  addShift() {
    const shiftGroup = this.fb.group({
      startTime: ['', Validators.required],
      endTime: ['', Validators.required]
    });
    this.shifts.push(shiftGroup);
  }

  removeShift(index: number): void {
    this.shifts.invalid
    this.shifts.removeAt(index);
  }

  private updateTimePeriod(): TimePeriod[] {
    return this.shifts.getRawValue().map(shift => ({
      startTime: shift.startTime,
      endTime: shift.endTime,
      active: true,
      edited: true
    } as TimePeriod));
  }

  private updateForm(): void {
    this.form.patchValue({
      dayOfWeek: this.workDay?.dayOfWeek,
    });

    this.shifts.clear();

    if (this.workDay?.shifts && this.workDay?.shifts.length > 0) {
      this.workDay?.shifts.forEach(shift => {
        this.shifts.push(this.fb.group({
          startTime: [shift.startTime, Validators.required],
          endTime: [shift.endTime, Validators.required]
        }));
      });
    }
  }

  canSubmit(): boolean {
    return (
      this.form.valid &&
      this.form.dirty
    );
  }

  confirm() {
    this.modalRef.confirm(
      {
        uuid: this.workDay?.uuid || StringUtils.generateUUID(),
        dayOfWeek: this.form.value.dayOfWeek,
        shifts: this.updateTimePeriod(),
        active: true,
        edited: true
      } as WorkDay
    );
  }

  cancel() {
    this.modalRef.dismiss()
  }

}
