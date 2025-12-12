import {HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {catchError, from, switchMap, throwError} from 'rxjs';
import AuthService from '../shared/services/auth/auth.service';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  let accessToken = localStorage.getItem('accessToken');
  if (accessToken) {
    try {
      accessToken = JSON.parse(accessToken);
    } catch {
    }
  }

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
            return throwError(() => error);
          }

          localStorage.setItem('accessToken', JSON.stringify(res.accessToken));

          const retryReq = req.clone({
            setHeaders: {
              Authorization: `Bearer ${res.accessToken}`,
            },
          });

          return next(retryReq);
        }),
        catchError((_) => {
          localStorage.clear();
          window.location.href = '/login';
          return throwError(() => error);
        })
      );
    })
  );
};
