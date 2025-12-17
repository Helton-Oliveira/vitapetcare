import {BaseEntity, IBaseEntity} from '../../../root/base-entity';
import {User} from '../user/user.model';
import {TimePeriod} from '../timePeriod/type-period.model';

export interface IWorkDay extends IBaseEntity {
  date?: Date;
  user?: User
  shifts?: TimePeriod[];
}

export class WorkDay extends BaseEntity implements IWorkDay {
  public date?: Date;
  public user?: User
  public shifts?: TimePeriod[];
}
