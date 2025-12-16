import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../../../environment';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  private readonly BASE_URL = `${environment.BASE_API_URL}/files`;
  private readonly _http = inject(HttpClient);

  async save(file: File): Promise<void> {
    this._http.post(`${this.BASE_URL}`, file)
  }
}
