import {BaseEntity, IBaseEntity} from '../../../root/base-entity';
import {WorkDay} from '../workDay/work-day-model';

export interface ITimePeriod extends IBaseEntity {
  startTime?: Date;
  endTime?: Date;
  workDay?: WorkDay;
}

export class TimePeriod extends BaseEntity implements ITimePeriod {
  public startTime?: Date;
  public endTime?: Date;
  public workDay?: WorkDay;
}
