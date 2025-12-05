import {IRole} from '../role/role-model';
import {BaseEntity, IBaseEntity} from '../../../root/base-entity';

export interface IUser extends IBaseEntity {
  name?: string;
  email?: string;
  password?: string;
  roles?: IRole[];
}

export class User extends BaseEntity implements IUser {
  public name?: string;
  public email?: string;
  public password?: string;
  public roles?: IRole[];
}
