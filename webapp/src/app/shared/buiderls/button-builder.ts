import {Injectable} from '@angular/core';
import {Button} from '../models/config/button';

@Injectable({providedIn: 'root'})
export class ButtonBuilder {

  static saveButton({title, isActive, action}: { title?: string, isActive?: boolean, action?: () => void }): Button {
    return new Button(
      title,
      'fa-solid fa-floppy-disk',
      '#3182ce',
      'text-white',
      'submit',
      isActive,
      action
    );
  }

  static add({title, isActive, action}: { title?: string, isActive?: boolean, action?: () => void }): Button {
    return new Button(
      title,
      'fa-solid fa-plus',
      '#4A9327',
      '',
      'submit',
      isActive,
      action
    );
  }

  static calendar({title, isActive, action}: { title?: string, isActive?: boolean, action?: () => void }): Button {
    return new Button(
      title,
      'fa-solid fa-calendar',
      'orange',
      'white',
      'submit',
      isActive,
      action
    );
  }

  static filter({title, isActive, action}: { title?: string, isActive?: boolean, action?: () => void }): Button {
    return new Button(
      title,
      'fa-solid fa-filter',
      'white',
      'gray',
      'submit',
      isActive,
      action
    );
  }

  static sort({title, isActive, action}: { title?: string, isActive?: boolean, action?: () => void }): Button {
    return new Button(
      title,
      'fa-solid fa-sort',
      'white',
      'gray',
      'submit',
      isActive,
      action
    );
  }

  static cancel({title, isActive, action}: { title?: string, isActive?: boolean, action?: () => void }): Button {
    return new Button(
      title,
      'fa-solid fa-ban',
      '#727D92',
      '#FFF',
      'submit',
      isActive,
      action
    );
  }


}
