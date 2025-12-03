import {Injectable} from '@angular/core';
import {Button} from '../models/config/button';

export type ButtonType = {
  title?: string,
  icon?: string,
  action?: () => void
}

@Injectable({providedIn: 'root'})
export class ButtonBuilder {

  static add(btn: ButtonType): Button {
    return new Button(
      btn?.title,
      btn?.icon,
      '#DF4G3',
      '',
      'submit',
      btn?.action
    );
  }

  static saveButton(btn: ButtonType): Button {
    return new Button(
      btn?.title,
      btn?.icon,
      '#DF4G3',
      '',
      'submit',
      btn?.action
    );
  }

  static calendar({title, action}: { title?: string, action?: () => void }): Button {
    return new Button(
      title,
      'fa-solid fa-calendar',
      'orange',
      'white',
      'submit',
      action
    );
  }

  static filter({title, action}: { title?: string, action?: () => void }): Button {
    return new Button(
      title,
      'fa-solid fa-filter',
      'white',
      'gray',
      'submit',
      action
    );
  }

  static sort({title, action}: { title?: string, action?: () => void }): Button {
    return new Button(
      title,
      'fa-solid fa-sort',
      'white',
      'gray',
      'submit',
      action
    );
  }


}
