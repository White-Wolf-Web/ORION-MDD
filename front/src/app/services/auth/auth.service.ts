import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private loginUrl = `${this.apiUrl}/login`; 

  constructor(private http: HttpClient, private router: Router) {}

  register(userData: {
    username: string;
    email: string;
    password: string;
  }): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, userData);
  }

  login(email: string, password: string): Observable<any> {
    return this.http.post(this.loginUrl, { email, password }, { responseType: 'text' })
      .pipe(tap((token: string) => { // Si vous ne connaissez pas le type exact, utilisez 'any' ou définissez un type spécifique
        localStorage.setItem('auth_token', token);
      }));
  }

  

  updateUser(userData: { username: string; email: string }): Observable<any> {
    return this.http.put(`${this.apiUrl}/updateUser`, userData);
  }

  logout(): void {
    localStorage.removeItem('token');
  }
}
