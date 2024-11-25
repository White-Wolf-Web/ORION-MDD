import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ArticleDto } from '../models/article.model';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private apiUrl = 'http://localhost:8080/api/articles';

  constructor(private http: HttpClient) {}

  // Méthode privée pour créer les en-têtes avec le token d'authentification
  private createAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token'); 
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  // Méthode pour récupérer tous les articles
  getAllArticles(): Observable<ArticleDto[]> {
    const headers = this.createAuthHeaders();
    return this.http.get<ArticleDto[]>(this.apiUrl, { headers });              // Effectue une requête GET pour un article spécifique
  }

  // Méthode pour récupérer un article spécifique par son ID
  getArticleById(id: string): Observable<ArticleDto> {
    const headers = this.createAuthHeaders();
    return this.http.get<ArticleDto>(`${this.apiUrl}/${id}`, { headers });     // Effectue une requête GET pour un article spécifique
  }

  // Méthode pour créer un nouvel article
  createArticle(article: ArticleDto): Observable<ArticleDto> {
    const headers = this.createAuthHeaders();
    return this.http.post<ArticleDto>(this.apiUrl, article, { headers });      // Effectue une requête POST pour créer un article

  }
}


