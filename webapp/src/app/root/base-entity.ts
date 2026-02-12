export interface IBaseEntity {
  id?: number;
  uuid?: string;
  active?: boolean;
  edited?: boolean;
  createdAt?: number;
  lastModifiedAt?: number;
  deletedAt?: number;
  createdBy?: string;
  lastModifiedBy?: string
}

export class BaseEntity implements IBaseEntity {
  public readonly id?: number;
  public uuid?: string;
  public active?: boolean;
  public edited?: boolean;
  public createdAt?: number = Date.now();
  public lastModifiedAt?: number = Date.now();
  public deletedAt?: number;
  public createdBy?: string;
  public lastModifiedBy?: string;
}
