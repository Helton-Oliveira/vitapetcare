import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../../../environment';
import {firstValueFrom} from 'rxjs';
import {IPage} from '../../dto/page-dto';
import {TimePeriod} from '../../models/timePeriod/time-period.model';

type EntityResponseType = TimePeriod
type EntityArrayResponseType = IPage<TimePeriod[]>

@Injectable({
  providedIn: 'root'
})
export class TimePeriodService {
  private readonly _http = inject(HttpClient)
  private readonly BASE_URL = `${environment.BASE_API_URL}/time-periods`;

  save(timePeriod: TimePeriod): Promise<EntityResponseType> {
    return firstValueFrom(this._http.post(this.BASE_URL, timePeriod))
  }

  update(timePeriod: TimePeriod): Promise<EntityResponseType> {
    return firstValueFrom(this._http.put(this.BASE_URL, timePeriod))
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
