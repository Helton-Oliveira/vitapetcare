import {v4 as uuidv4} from 'uuid';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StringUtils {


  static generateUUID(): string {
    return uuidv4()
  }
}
