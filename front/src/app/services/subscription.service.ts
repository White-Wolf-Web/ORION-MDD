import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { SubscriptionDto } from '../models/subscription.model';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  private createAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  getAllSubscriptions(): Observable<SubscriptionDto[]> {
    const headers = this.createAuthHeaders();
    return this.http.get<SubscriptionDto[]>(`${this.apiUrl}/topics`, { headers });
  }

  subscribeToTopic(topicId: number): Observable<any> {
    const headers = this.createAuthHeaders();
    return this.http.post(`${this.apiUrl}/users/topics/${topicId}`, {}, { headers });
  }

  getTopics(): Observable<{ id: number; name: string }[]> {
    return this.http.get<{ id: number; name: string }[]>(this.apiUrl);
  }

 

  getSubscriptionById(id: string): Observable<SubscriptionDto> {
    const headers = this.createAuthHeaders();
    return this.http.get<SubscriptionDto>(`${this.apiUrl}/${id}`, { headers });
  }

  subscribe(subscriptionDto: SubscriptionDto): Observable<SubscriptionDto> {
    const headers = this.createAuthHeaders();
    return this.http.post<SubscriptionDto>(`${this.apiUrl}/subscribe`, subscriptionDto, { headers });
  }



  getUserSubscriptions(): Observable<SubscriptionDto[]> {
    const headers = this.createAuthHeaders();
    return this.http.get<SubscriptionDto[]>(`${this.apiUrl}/topics`, { headers });
  }


  unsubscribeFromTopic(subscriptionId: number): Observable<any> {
    const headers = this.createAuthHeaders();
    return this.http.delete(`${this.apiUrl}/users/subscriptions/${subscriptionId}`, {
        headers,
        responseType: 'text' 
    });
}

  
}
