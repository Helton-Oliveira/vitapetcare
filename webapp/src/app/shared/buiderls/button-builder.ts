import {Injectable} from '@angular/core';
import {Button} from '../models/config/button';

@Injectable({providedIn: 'root'})
export class ButtonBuilder {

  static saveButton({title, action}: { title?: string, action?: () => void }): Button {
    return new Button(
      title,
      'fa-solid fa-floppy-disk',
      '#3182ce',
      'text-white',
      'submit',
      action
    );
  }

  static add({title, action}: { title?: string, action?: () => void }): Button {
    return new Button(
      title,
      'fa-solid fa-plus',
      '#4A9327',
      '',
      'submit',
      action
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

  static cancel({title, action}: { title?: string, action?: () => void }): Button {
    return new Button(
      title,
      'fa-solid fa-ban',
      '#727D92',
      '#FFF',
      'submit',
      action
    );
  }


}
