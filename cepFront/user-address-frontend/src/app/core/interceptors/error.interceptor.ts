import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);

  return next(req).pipe(
    catchError(error => {
      let errorMessage = 'An unknown error occurred!';
      
      if (error.error?.message) {
        errorMessage = error.error.message;
      } else if (error.error?.errors) {
        errorMessage = Object.values(error.error.errors).join('\n');
      }

      if (error.status === 401) {
        router.navigate(['/auth/login']);
      }

      return throwError(() => error);
    })
  );
};