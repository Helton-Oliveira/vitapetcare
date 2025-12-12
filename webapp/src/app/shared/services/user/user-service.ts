import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../../../environment';
import {User} from '../../models/user/user.model';
import {firstValueFrom} from 'rxjs';
import {Page} from '../../dto/page-dto';

type EntityResponseType = User
type EntityArrayResponseType = Page<User[]>

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly _http = inject(HttpClient)
  private readonly BASE_URL = `${environment.BASE_API_URL}/users`;

  save(user: User): Promise<EntityResponseType> {
    return firstValueFrom(this._http.post(this.BASE_URL, user))
  }

  update(user: User): Promise<EntityResponseType> {
    return firstValueFrom(this._http.put(this.BASE_URL, user))
  }

  getAll(): Promise<EntityArrayResponseType> {
    return firstValueFrom(this._http.get<EntityArrayResponseType>(this.BASE_URL))
  }

  get(id: string): Promise<EntityResponseType> {
    return firstValueFrom(this._http.get(`${this.BASE_URL}/${id}`))
  }

  disable(id: string): Promise<EntityResponseType> {
    return firstValueFrom(this._http.delete(`${this.BASE_URL}/${id}`));
  }

}
