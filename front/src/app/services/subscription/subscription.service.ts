import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { Subscription } from 'src/app/models/subscription.model';

@Injectable({
  providedIn: 'root',
})
export class SubscriptionService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getSubscriptionsForUser(): Observable<Subscription[]> {
    return this.http.get<Subscription[]>(`${this.apiUrl}/me`, {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${this.authService.getToken()}`
      })
    });
  }
}












/* import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Subscription } from 'src/app/models/subscription.model';
import { AuthService } from '../auth/auth.service';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  private apiUrl = 'http://localhost:8080/api/topics';
  constructor(private http: HttpClient, private authService: AuthService) {}

  // Cette fonction simule la récupération des abonnements depuis un backend
  getSubscriptionsForUser(): Observable<Subscription[]> {
    return of([
      { id: 1, title: 'Abonnement 1', description: 'Description de l\'abonnement 1' },
      { id: 2, title: 'Abonnement 2', description: 'Description de l\'abonnement 2' },
      { id: 3, title: 'Abonnement 3', description: 'Description de l\'abonnement 3' },
      { id: 4, title: 'Abonnement 4', description: 'Description de l\'abonnement 4' }
    ]);
  }

  subscribeToTheme(themeId: number): Observable<boolean> {
    // Simuler un appel API pour s'abonner
    console.log(`Subscribed to theme with ID: ${themeId}`);
    return of(true); // Simuler une réponse de succès
  }

  unsubscribeFromTheme(themeId: number): Observable<boolean> {
    // Simuler un appel API pour se désabonner
    console.log(`Unsubscribed from theme with ID: ${themeId}`);
    return of(true); // Simuler une réponse de succès
  }
  
  subscribeToTopic(topicId: number): Observable<any> {
    const url = `${this.apiUrl}/subscribe/${topicId}`;
    return this.http.post(url, {}, {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${this.authService.getToken()}`
      })
    });
  }

  unsubscribeFromTopic(topicId: number): Observable<any> {
    const url = `${this.apiUrl}/unsubscribe/${topicId}`;
    return this.http.post(url, {}, {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${this.authService.getToken()}`
      })
    });
  }
}
*/