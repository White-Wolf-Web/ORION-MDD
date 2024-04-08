import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { Login } from 'src/app/models/login.model';
import { Register } from 'src/app/models/register.model';
import { AuthResponse } from 'src/app/models/authResponse.model';
import { User } from 'src/app/models/user.model';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private loginUrl = `${this.apiUrl}/login`; 
private registerUrl = `${this.apiUrl}/register`; 
  constructor(private http: HttpClient, private router: Router) {}

  register(userData: Register): Observable<any> {
    return this.http.post(`${this.registerUrl}`, userData);
  }
  

  login(userData: Login): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(this.loginUrl, userData)
      .pipe(tap((authResponse: AuthResponse) => { 
        localStorage.setItem('auth_token', authResponse.token); 
      }));
  }
  

  logout(): void {
    localStorage.removeItem('auth_token');
  }

  getToken(): string | null {
    return localStorage.getItem('auth_token');
  }

  getCurrentUser(): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/me`); 
  }

  updateUser(userData: Partial<User>): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/me`, userData, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.getToken()}`
      })
    });
  }

  
  
}
