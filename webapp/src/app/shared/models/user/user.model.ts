import {BaseEntity, IBaseEntity} from '../../../root/base-entity';
import {Role} from './role';
import {FileApp} from '../file/file-app-model';
import {WorkDay} from '../workDay/work-day-model';

export interface IUser extends IBaseEntity {
  name?: string;
  email?: string;
  password?: string;
  role?: Role;
  permissions?: string[];
  files?: FileApp[];
  workDays?: WorkDay[];
}

export class User extends BaseEntity implements IUser {
  public name?: string;
  public email?: string;
  public password?: string;
  public role?: Role;
  public permissions?: string[];
  public files?: FileApp[];
  public workDays?: WorkDay[];
}
