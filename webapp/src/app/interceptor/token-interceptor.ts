import {inject} from '@angular/core';
import AuthService from '../shared/services/auth/auth.service';
import {HttpInterceptorFn} from '@angular/common/http';
import {catchError, from, switchMap, throwError} from 'rxjs';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  console.log(`Interceptando: ${req.method} ${req.url}`);

  if (req.url.includes('cloudinary.com')) {
    console.log('Ignorando Cloudinary...');
    return next(req);
  }

  const authService = inject(AuthService);

  let accessToken = localStorage.getItem('accessToken') ||
    sessionStorage.getItem('accessToken');

  const clonedReq = accessToken
    ? req.clone({
      setHeaders: {Authorization: `Bearer ${accessToken}`},
    })
    : req;

  return next(clonedReq).pipe(
    catchError((error) => {
      console.log(error.status, error.message)
      if (error.status !== 401) return throwError(() => error);

      return from(authService.refreshToken()).pipe(
        switchMap((res) => {
          if (!res.accessToken) {
            return throwError(() => error);
          }

          sessionStorage.setItem('accessToken', JSON.stringify(res.accessToken));
          localStorage.setItem('accessToken', JSON.stringify(res.accessToken));

          const accessToken = localStorage.getItem('accessToken') || sessionStorage.getItem('accessToken')

          const retryReq = req.clone({
            setHeaders: {
              Authorization: `Bearer ${accessToken}`,
            },
          });

          return next(retryReq);
        }),
        catchError((err) => {
          console.warn(err.message)
          localStorage.clear();
          sessionStorage.clear();
          window.location.href = '/login';
          return throwError(() => error);
        })
      );
    })
  );
};
