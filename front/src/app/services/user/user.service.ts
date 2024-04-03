import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  getCurrentUser(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/current`);
  }

  updateUser(userData: { username: string; email: string }): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/update`, userData);
  }
}
