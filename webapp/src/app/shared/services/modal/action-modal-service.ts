import {ActionModalComponent} from './action-modal-component';
import {GenericModalService} from './generic-modal-service';
import {Injectable} from '@angular/core';

@Injectable({providedIn: 'root'})
export class ActionEditorModalService {
  constructor(private modal: GenericModalService) {
  }

  show(action: () => void, size: 'sm' | 'md' | 'lg' | 'xl' = 'sm') {
    this.modal
      .show(ActionModalComponent, {size})
      .then(() => action())
      .catch(() => {
      });
  }
}
