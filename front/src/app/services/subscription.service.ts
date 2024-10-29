import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SubscriptionDto } from '../models/subscription.model';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  private apiUrl = 'http://localhost:8080/api/subscriptions';

  constructor(private http: HttpClient) {}

  getAllSubscriptions(): Observable<SubscriptionDto[]> {
    return this.http.get<SubscriptionDto[]>(`${this.apiUrl}`);
  }


  getSubscriptionById(id: string): Observable<SubscriptionDto> {
    return this.http.get<SubscriptionDto>(`${this.apiUrl}/${id}`);
  }

  subscribe(subscriptionDto: SubscriptionDto): Observable<SubscriptionDto> {
    return this.http.post<SubscriptionDto>(`${this.apiUrl}/subscribe`, subscriptionDto);
  }

  unsubscribe(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/unsubscribe/${id}`);
  }
}
