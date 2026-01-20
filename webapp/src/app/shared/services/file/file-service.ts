import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../../../environment';
import {FileApp} from '../../models/file/file-app-model';
import {firstValueFrom} from 'rxjs';


type EntityResponseType = FileApp;
type EntityArrayResponseType = FileApp[];

@Injectable({
  providedIn: 'root'
})
export class FileService {
  private readonly BASE_URL = `${environment.BASE_API_URL}/files`;
  private readonly _http = inject(HttpClient);

  async save(file: File): Promise<void> {
    this._http.post(`${this.BASE_URL}`, file)
  }

  async findAllByUserId(userId: number): Promise<EntityArrayResponseType> {
    return firstValueFrom(
      this._http.get<EntityArrayResponseType>(`${this.BASE_URL}/find-by-user/${userId}`)
    );
  }
}
