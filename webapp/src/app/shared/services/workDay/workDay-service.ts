import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../../../environment';
import {firstValueFrom} from 'rxjs';
import {IPage} from '../../dto/page-dto';
import {WorkDay} from '../../models/workDay/work-day-model';

type EntityResponseType = WorkDay
type EntityArrayResponseType = IPage<WorkDay[]>

@Injectable({
  providedIn: 'root'
})
export class WorkDayService {
  private readonly _http = inject(HttpClient)
  private readonly BASE_URL = `${environment.BASE_API_URL}/work-days`;

  save(workDay: WorkDay): Promise<EntityResponseType> {
    return firstValueFrom(this._http.post(this.BASE_URL, workDay))
  }

  update(workDay: WorkDay): Promise<EntityResponseType> {
    return firstValueFrom(this._http.put(this.BASE_URL, workDay))
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
