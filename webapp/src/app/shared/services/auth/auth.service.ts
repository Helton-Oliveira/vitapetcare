import {inject, Injectable} from '@angular/core';
import {environment } from '../../../../../../environment'
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export default class AuthService {
  private readonly _http = inject(HttpClient)
  private readonly BASE_URL = environment.BASE_API_URL;
}
