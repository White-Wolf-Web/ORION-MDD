import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { CanActivateFn } from '@angular/router';

export const UnAuthGuard: CanActivateFn = () => {
  const router = inject(Router);
  const token = localStorage.getItem('token');

  if (token) {
    // Redirige vers /me si l'utilisateur est connecté
    router.navigate(['/me']);
    return false;
  }

  // Sinon, autorise l'accès
  return true;
};
