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

  private createAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token'); 
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  getAllArticles(): Observable<ArticleDto[]> {
    const headers = this.createAuthHeaders();
    return this.http.get<ArticleDto[]>(this.apiUrl, { headers });
  }

  getArticleById(id: string): Observable<ArticleDto> {
    const headers = this.createAuthHeaders();
    return this.http.get<ArticleDto>(`${this.apiUrl}/${id}`, { headers });
  }

  createArticle(article: ArticleDto): Observable<ArticleDto> {
    const headers = this.createAuthHeaders();
    return this.http.post<ArticleDto>(this.apiUrl, article, { headers });
  }
}


