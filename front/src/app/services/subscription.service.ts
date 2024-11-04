import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SubscriptionDto } from '../models/subscription.model';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  private apiUrl = 'http://localhost:8080/api/topics';

  constructor(private http: HttpClient) {}

  private createAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token'); 
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  getAllSubscriptions(): Observable<SubscriptionDto[]> {
    const headers = this.createAuthHeaders();
    return this.http.get<SubscriptionDto[]>(this.apiUrl, { headers });
  }

  getSubscriptionById(id: string): Observable<SubscriptionDto> {
    const headers = this.createAuthHeaders();
    return this.http.get<SubscriptionDto>(`${this.apiUrl}/${id}`, { headers });
  }

  subscribe(subscriptionDto: SubscriptionDto): Observable<SubscriptionDto> {
    const headers = this.createAuthHeaders();
    return this.http.post<SubscriptionDto>(`${this.apiUrl}/subscribe`, subscriptionDto, { headers });
  }

  subscribeToTopic(subscriptionId: number): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.post(`${this.apiUrl}/subscribe`, { subscriptionId }, { headers });
  }
  
  

  getUserSubscriptions(): Observable<SubscriptionDto[]> {
    const headers = this.createAuthHeaders();
    return this.http.get<SubscriptionDto[]>(`${this.apiUrl}/my-subscriptions`, { headers });
  }

  unsubscribe(id: string): Observable<void> {
    const headers = this.createAuthHeaders();
    return this.http.delete<void>(`${this.apiUrl}/unsubscribe/${id}`, { headers });
  }
}
