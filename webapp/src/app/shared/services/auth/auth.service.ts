import {inject, Injectable} from '@angular/core';
import {environment} from '../../../../../../environment'
import {HttpClient} from '@angular/common/http';
import {firstValueFrom} from 'rxjs';
import {LoginRequest, LoginResponse, TokenResponse} from '../../models/auth/auth-dto';
import {User} from '../../models/user/user.model';

type PermissionsArrayResponseType = Permissions[];

@Injectable({
  providedIn: 'root'
})
export default class AuthService {
  private readonly _http = inject(HttpClient)
  private readonly BASE_URL = `${environment.BASE_API_URL}/auth`;
  private isAuthenticated = false;

  login(loginRequest: LoginRequest): Promise<LoginResponse> {
    return firstValueFrom(this._http.post(`${this.BASE_URL}/login`, loginRequest));
  }

  refreshToken(): Promise<TokenResponse> {
    const refreshToken = localStorage.getItem('refreshToken');

    return firstValueFrom(
      this._http.post<TokenResponse>(`${this.BASE_URL}/refresh`, {refreshToken})
    );
  }

  getCurrentAccount(): Promise<User> {
    return firstValueFrom(this._http.get(`${this.BASE_URL}/current-account`));
  }

  getAllPermissions(): Promise<PermissionsArrayResponseType> {
    return firstValueFrom(this._http.get<PermissionsArrayResponseType>(`${this.BASE_URL}/permissions`));
  }

  isAuthenticatedUser(): boolean {
    return this.isAuthenticated
  }

  logout(): void {
    localStorage.clear();
    this.isAuthenticated = false;
  }
}
