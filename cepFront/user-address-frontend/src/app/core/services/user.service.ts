import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = `${environment.apiUrl}/addresses`;
  private apiUrl2 = `${environment.apiUrl}/users`;

  constructor(private http: HttpClient) { }

  loadAllAddresses(pageIndex: number, pageSize: number, sortBy: string): Observable<any> {
    const token = localStorage.getItem('auth_token') || sessionStorage.getItem('auth_token');

    if (!token) {
      return throwError(() => new Error('Token not found'));
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.get<any>(
      `${this.apiUrl}?page=${pageIndex}&size=${pageSize}&sortBy=${sortBy}`,
      {
        headers: headers,
        withCredentials: true
      }
    ).pipe(
      catchError(error => {
        console.error('Erro completo:', error);
        if (error.status === 0) {
          console.error('Possível erro de CORS - Verifique a configuração do backend');
        }
        return throwError(() => error);
      })
    );
  }

  deleteAddress(): Observable<void> {
    const id = localStorage.getItem('user_id') || sessionStorage.getItem('user_id');
    const token = localStorage.getItem('auth_token') || sessionStorage.getItem('auth_token');

    if (!token) {
      return throwError(() => new Error('Token not found'));
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.delete<void>(`${this.apiUrl}/${id}`, {
      headers: headers,
      withCredentials: true
    }).pipe(
      catchError(error => {
        console.error('Error deleting address:', error);
        return throwError(() => error);
      })
    );
  }

  getUserIdToDelete(email: string): Observable<any> {

    const token = localStorage.getItem('auth_token') || sessionStorage.getItem('auth_token');

    if (!token) {
      return throwError(() => new Error('Token not found'));
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.get<any>(
      `${this.apiUrl2}/email/${email}`,
      {
        headers: headers,
        withCredentials: true
      }
    ).pipe(
      catchError(error => {
        console.error('Error fetching user by email:', error);
        return throwError(() => error);
      })
    );
  }

  createAddress(addressData: any): Observable<any> {
    const id = localStorage.getItem('user_id') || sessionStorage.getItem('user_id');
    const token = localStorage.getItem('auth_token') || sessionStorage.getItem('auth_token');

    if (!token) {
      return throwError(() => new Error('Token not found'));
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.post<any>(this.apiUrl, addressData, { headers });
  }

}

