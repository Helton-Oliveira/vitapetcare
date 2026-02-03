import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../../../environment';
import {Job} from '../../models/job/job.model';
import {firstValueFrom} from 'rxjs';
import {IPage} from '../../dto/page-dto';

type EntityResponseType = Job
type EntityArrayResponseType = IPage<Job[]>

@Injectable({
  providedIn: 'root'
})
export class JobService {
  private readonly _http = inject(HttpClient)
  private readonly BASE_URL = `${environment.BASE_API_URL}/jobs`;

  save(job: Job): Promise<EntityResponseType> {
    return firstValueFrom(this._http.post(this.BASE_URL, job))
  }

  update(job: Job): Promise<EntityResponseType> {
    return firstValueFrom(this._http.put(this.BASE_URL, job))
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
