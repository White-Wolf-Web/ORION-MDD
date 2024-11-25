import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserDto } from '../models/user.model';
import { SubscriptionDto } from '../models/subscription.model';

@Injectable({
  providedIn: 'root' 
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/me'; 

  constructor(private http: HttpClient) {}                 // Le HttpClient est injecté pour effectuer des requêtes HTTP.

  // Récupère la liste de tous les utilisateurs
  getAllUsers(): Observable<UserDto[]> {                   
    return this.http.get<UserDto[]>(this.apiUrl);          // Effectue une requête GET vers l'API pour obtenir tous les utilisateurs.
  }

  // Récupère un utilisateur spécifique par son ID
  getUserById(id: string): Observable<UserDto> {           
    return this.http.get<UserDto>(`${this.apiUrl}/${id}`); // Construit l'URL avec l'ID et effectue une requête GET.
  }

  // Met à jour le profil de l'utilisateur connecté
  updateUserProfile(user: { username: string; email: string }): Observable<UserDto> {    
    const token = localStorage.getItem('token');                                         // Récupère le token JWT stocké localement.
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);           // Ajoute le token dans l'en-tête pour prouver que l'utilisateur est authentifié.
    return this.http.put<UserDto>(this.apiUrl, user, { headers });                       // Effectue une requête PUT vers l'API pour mettre à jour les données utilisateur.
  }

  // Récupère les abonnements de l'utilisateur connecté
  getUserSubscriptions(): Observable<SubscriptionDto[]> {                      
    const token = localStorage.getItem('token');                               // Récupère le token JWT stocké localement.
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`); // Ajoute le token dans l'en-tête.
    return this.http.get<SubscriptionDto[]>('http://localhost:8080/api/users/me/subscriptions', { headers }); // Effectue une requête GET vers l'API pour obtenir la liste des abonnements de l'utilisateur.
  }
}
