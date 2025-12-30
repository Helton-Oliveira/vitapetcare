import {BaseEntity, IBaseEntity} from '../../../root/base-entity';
import {User} from '../user/user.model';
import {TimePeriod} from '../timePeriod/time-period.model';
import {DayOfWeek} from './day-of-week.enum';

export interface IWorkDay extends IBaseEntity {
  dayOfWeek?: DayOfWeek;
  user?: User
  shifts?: TimePeriod[];
}

export class WorkDay extends BaseEntity implements IWorkDay {
  public dayOfWeek?: DayOfWeek;
  public user?: User
  public shifts?: TimePeriod[];

  static getAllDays(): DayOfWeek[] {
    return [
      DayOfWeek.MONDAY,
      DayOfWeek.TUESDAY,
      DayOfWeek.WEDNESDAY,
      DayOfWeek.THURSDAY,
      DayOfWeek.FRIDAY,
      DayOfWeek.SATURDAY,
      DayOfWeek.SUNDAY,
    ];
  }
}
