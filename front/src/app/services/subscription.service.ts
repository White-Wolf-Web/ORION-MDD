import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SubscriptionDto } from '../models/subscription.model';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  private apiUrl = 'http://localhost:8080/api/subscriptions';

  constructor(private http: HttpClient) {}

  getAllSubscriptions(headers: HttpHeaders): Observable<SubscriptionDto[]> {
    return this.http.get<SubscriptionDto[]>(this.apiUrl, { headers });
  }


  getSubscriptionById(id: string): Observable<SubscriptionDto> {
    return this.http.get<SubscriptionDto>(`${this.apiUrl}/${id}`);
  }

  subscribe(subscriptionDto: SubscriptionDto): Observable<SubscriptionDto> {
    return this.http.post<SubscriptionDto>(`${this.apiUrl}/subscribe`, subscriptionDto);
  }

  subscribeToTopic(subscriptionId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/subscribe`, { id: subscriptionId });
  }

  getUserSubscriptions(): Observable<SubscriptionDto[]> {
    return this.http.get<SubscriptionDto[]>(`${this.apiUrl}/my-subscriptions`);
  }

  unsubscribe(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/unsubscribe/${id}`);
  }
}
