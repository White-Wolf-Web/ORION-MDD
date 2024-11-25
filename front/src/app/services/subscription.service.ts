import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { SubscriptionDto } from '../models/subscription.model';

@Injectable({
  providedIn: 'root',
})
export class SubscriptionService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  // Méthode privée pour créer les en-têtes d'authentification
  private createAuthHeaders(): HttpHeaders {     
    const token = localStorage.getItem('token'); // Récupère le token JWT stocké localement
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,          // Ajoute le token dans l'en-tête Authorization
    });
  }

    // Récupère tous les thèmes disponibles (topics)
  getAllSubscriptions(): Observable<SubscriptionDto[]> {
    const headers = this.createAuthHeaders();
    return this.http.get<SubscriptionDto[]>(`${this.apiUrl}/topics`, {
      headers,
    });
  }

  // Abonne l'utilisateur à un thème spécifique
  subscribeToTopic(topicId: number): Observable<void> {
    const headers = this.createAuthHeaders();
    return this.http.post<void>(
      `${this.apiUrl}/users/topics/${topicId}`,
      {},                                        // Corps vide car aucune donnée supplémentaire n'est nécessaire
      { headers }
    );
  }

// Récupère tous les sujets ou thèmes
  getTopics(): Observable<{ id: number; name: string }[]> {
    return this.http.get<{ id: number; name: string }[]>(this.apiUrl);
  }

  // Récupère les détails d'un abonnement par son ID
  getSubscriptionById(id: string): Observable<SubscriptionDto> {
    const headers = this.createAuthHeaders();
    return this.http.get<SubscriptionDto>(`${this.apiUrl}/${id}`, { headers });
  }

  // Abonne un utilisateur en envoyant un objet `SubscriptionDto`
  subscribe(subscriptionDto: SubscriptionDto): Observable<SubscriptionDto> {
    const headers = this.createAuthHeaders();
    return this.http.post<SubscriptionDto>(
      `${this.apiUrl}/subscribe`,
      subscriptionDto,
      { headers }
    );
  }

  // Récupère les abonnements de l'utilisateur connecté
  getUserSubscriptions(): Observable<SubscriptionDto[]> {
    const headers = this.createAuthHeaders();
    return this.http.get<SubscriptionDto[]>(
      `${this.apiUrl}/users/me/subscriptions`,
      { headers }
    );
  }

  // Désabonne l'utilisateur d'un thème spécifique
  unsubscribeFromTopic(subscriptionId: number): Observable<{ message: string }> {
    const headers = this.createAuthHeaders();
    return this.http.delete<{ message: string }>(
      `${this.apiUrl}/users/subscriptions/${subscriptionId}`,
      {
        headers,
      }
    );
  }
  
  
}
