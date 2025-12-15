import {inject} from '@angular/core';
import {CanActivateFn, Router} from '@angular/router';

export const authGuard: CanActivateFn = async () => {
  const router = inject(Router);
  const isLoggedIn = localStorage.getItem('accessToken') || sessionStorage.getItem('accessToken');

  if (isLoggedIn != null) {
    return true;
  } else {
    await router.navigate(['/login'])
    return false;
  }
};
