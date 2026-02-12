import {BaseEntity, IBaseEntity} from '../../../root/base-entity';
import {Role} from '../role/role.enum';
import {JobType} from './JobType';
import {Status} from '../../enumerations/Status';

export interface IJob extends IBaseEntity {
  name?: string;
  valueInCents?: number;
  duration?: number;
  type?: JobType;
  status?: Status;
  hasReturn?: boolean;
  typesOfProfessionals?: Role;
  timeToReturnInDays?: number;
}

export class Job extends BaseEntity implements IJob {
  public name?: string;
  public valueInCents?: number;
  public duration?: number;
  public type?: JobType;
  public status?: Status;
  public hasReturn?: boolean;
  public typesOfProfessionals?: Role;
  public timeToReturnInDays?: number;

  static getTypesOfProfessionals(): Role[] {
    return [
      Role.RECEPTIONIST,
      Role.GROOMER,
      Role.VETERINARIAN
    ]
  }
}
