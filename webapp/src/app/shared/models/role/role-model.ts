import {User} from '../user/user.model';
import {BaseEntity, IBaseEntity} from '../../../root/base-entity';

export interface IRole extends IBaseEntity {
  name?: string;
  permissions?: Set<string>;
  users?: Set<User[]>;
}

export class Role extends BaseEntity implements IRole {
  public name?: string;
  public permissions?: Set<string>;
  public users?: Set<User[]>;
}
