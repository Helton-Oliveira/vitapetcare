import {BaseEntity, IBaseEntity} from '../../../root/base-entity';
import {WorkDay} from '../workDay/work-day-model';

export interface ITimePeriod extends IBaseEntity {
  startTime?: string;
  endTime?: string;
  workDay?: WorkDay;
}

export class TimePeriod extends BaseEntity implements ITimePeriod {
  public startTime?: string;
  public endTime?: string;
  public workDay?: WorkDay;
}
