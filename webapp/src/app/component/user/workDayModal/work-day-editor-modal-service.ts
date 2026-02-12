import {Injectable} from '@angular/core';
import {GenericModalService} from '../../../shared/services/modal/generic-modal-service';
import {WorkDayEditorModal} from './work-day-editor-modal';
import {WorkDay} from '../../../shared/models/workDay/work-day-model';
import {User} from '../../../shared/models/user/user.model';

@Injectable({
  providedIn: 'root'
})
export class WorkDayEditorModalService {
  constructor(private genericModalService: GenericModalService) {
  }

  show(user?: User, workDay?: WorkDay, callback?: (workDay: WorkDay) => void) {
    this.genericModalService
      .show(WorkDayEditorModal, {
        size: 'xl',
        inputs: {user, workDay}
      })
      .then(result => {
        if (callback) callback(result);
        return result;
      })
      .catch(() => {
      });
  }
}
