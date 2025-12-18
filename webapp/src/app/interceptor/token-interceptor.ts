import {inject} from '@angular/core';
import AuthService from '../shared/services/auth/auth.service';
import {HttpInterceptorFn} from '@angular/common/http';
import {catchError, from, switchMap, throwError} from 'rxjs';
import {environment} from '../../../../environment';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  console.warn(`Interceptando: ${req.method} ${req.url}`);
  const authService = inject(AuthService);

  if (!req.url.startsWith(environment.BASE_API_URL)) {
    return next(req);
  }

  let accessToken =
    localStorage.getItem('accessToken') ||
    sessionStorage.getItem('accessToken');

  const clonedReq = accessToken
    ? req.clone({
      setHeaders: {Authorization: `Bearer ${accessToken}`},
    })
    : req;

  return next(clonedReq).pipe(
    catchError((error) => {
      if (error.status !== 401) return throwError(() => error);

      return from(authService.refreshToken()).pipe(
        switchMap((res) => {
          if (!res.accessToken) {
            throw error;
          }

          sessionStorage.setItem('accessToken', res.accessToken);
          localStorage.setItem('accessToken', res.accessToken);

          const accessToken = localStorage.getItem('accessToken') || sessionStorage.getItem('accessToken')

          const retryReq = req.clone({
            setHeaders: {
              Authorization: `Bearer ${accessToken}`,
            },
          });

          return next(retryReq);
        }),
        catchError(() => {
          authService.logout();
          window.location.href = '/login';
          return throwError(() => error);
        })
      );
    })
  );
};


