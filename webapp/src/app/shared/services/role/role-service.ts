import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../../../environment';
import {Page} from '../../dto/page-dto';
import {Role} from '../../models/role/role-model';
import {firstValueFrom} from 'rxjs';

type EntityResponseType = Role
type EntityArrayResponseType = Page<Role[]>

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private readonly _http = inject(HttpClient)
  private readonly BASE_URL = `${environment.BASE_API_URL}/roles`;

  save(role: Role): Promise<EntityResponseType> {
    return firstValueFrom(this._http.post(this.BASE_URL, role))
  }
  
  update(role: Role): Promise<EntityResponseType> {
    return firstValueFrom(this._http.put(this.BASE_URL, role))
  }

  getAll(): Promise<EntityArrayResponseType> {
    return firstValueFrom(this._http.get<EntityArrayResponseType>(this.BASE_URL))
  }

  get(id: string): Promise<EntityResponseType> {
    return firstValueFrom(this._http.get(`${this.BASE_URL}/${id}`))
  }

}
