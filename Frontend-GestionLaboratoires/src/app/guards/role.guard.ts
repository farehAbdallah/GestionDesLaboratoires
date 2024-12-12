import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';

export const roleGuard: CanActivateFn = (route, state) => {
  const loginService = inject(LoginService);  // Inject LoginService
  const router = inject(Router);  // Inject Router

  const logedUser = loginService.loggedUser;  // Get the logged-in user from the service
  const requiredRoles = route.data['requiredRole'];  // Get the required roles from the route's data

  if (logedUser && requiredRoles && requiredRoles.includes(logedUser.role)) {
    return true;  // Allow access if the user's role is included in requiredRoles
  } else {
    router.navigate(['/login']);  // Redirect to login if roles don't match
    return false;  // Prevent access
  }
};
