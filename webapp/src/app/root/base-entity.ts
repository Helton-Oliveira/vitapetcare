export interface IBaseEntity {
    id?: number;
    uuid?: string;
    active?: boolean;
    _edited?: boolean;
    createdAt?: number;
    lastModifiedAt?: number;
    deletedAt?: number;
    createdBy?: string;
    lastModifiedBy?: string
}

export class BaseEntity implements IBaseEntity {
    public readonly id?: number;
    public uuid?: string;
    public active?: boolean = true;
    public _edited?: boolean = false;
    public createdAt?: number = Date.now();
    public lastModifiedAt?: number = Date.now();
    public deletedAt?: number;
    public createdBy?: string;
    public lastModifiedBy?: string;
}