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

  /*  validateField(field: string, form: FormGroup): string {
      if (form.get(field)?.touched && form.get(field)?.invalid) {
        return `
          <div class="flex items-center gap-1 -mt-3 w-full">
            <i class="fa-solid fa-circle-exclamation text-rose-600"></i>
            <span class="text-sm text-red-800">
            {{ "global.field.required" | translate: {field: 'global.field.${field}' | translate} }}</span>
          </div>
        `
      }
      return '';
    } */
}
