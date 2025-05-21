import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}`;

  login(email: string, password: string): Observable<any> {
    return this.http.post<{ token: string }>(`${this.apiUrl}/auth/login`, { email, password }).pipe(
      tap(response => {
        sessionStorage.setItem('auth_token', response.token);
      })
    );
  }

  register(name: string, email: string, password: string, role: 'ADMIN' | 'USER' = 'USER'): Observable<any> {
    return this.http.post<{ token: string }>(`${this.apiUrl}/auth/register`, {
      name,
      email,
      password,
      role
    }).pipe(
      tap(response => {
        sessionStorage.setItem('auth_token', response.token);
      })
    );
  }

  logout(): void {
    localStorage.clear();
    sessionStorage.clear();
  }

}