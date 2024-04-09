import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Topic } from 'src/app/models/topic.model'; 
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class TopicsService {
  private apiUrl = 'http://localhost:8080/api/topics';

  constructor(private httpClient: HttpClient, private authService: AuthService) {}

  getTopics(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.apiUrl}`);
  }

  subscribeToTopic(topicId: number): Observable<any> {
    return this.httpClient.post(`${this.apiUrl}/subscribe/${topicId}`, {});
  }

  unsubscribeFromTopic(topicId: number): Observable<any> {
    return this.httpClient.post(`${this.apiUrl}/unsubscribe/${topicId}`, {});
  }

  getSubscribedTopics(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.apiUrl}/me/subscribed`);
  }
  
  getSubscribedTopicsForUser(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.apiUrl}/me/subscribed`, {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${this.authService.getToken()}`
      })
    });
  }
}