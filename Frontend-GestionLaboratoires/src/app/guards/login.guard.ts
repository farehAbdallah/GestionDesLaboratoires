import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';

export const loginGuard: CanActivateFn = (route, state) => {
  const loginService = inject(LoginService); // Inject the LoginService
  const router = inject(Router); // Inject the Router

  return loginService.getLoged().pipe(
    map(logedUser => {
      // Check if logedUser.id is not an empty string
      if (logedUser.id !== '') {
        return true; // Allow navigation
      } else {
        router.navigateByUrl('/login'); // Redirect to login if not logged in
        return false; // Prevent navigation
      }
    })
  );
};
