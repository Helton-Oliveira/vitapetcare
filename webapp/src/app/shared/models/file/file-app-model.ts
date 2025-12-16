import {User} from '../user/user.model';
import {BaseEntity, IBaseEntity} from '../../../root/base-entity';
import {FileType} from './file-type';

export interface IFileApp extends IBaseEntity {
  name?: string
  path?: string;
  owner?: User;
  type?: FileType;
}

export class FileApp extends BaseEntity implements IFileApp {
  public name?: string
  public path?: string;
  public owner?: User;
  public type?: FileType;
}
