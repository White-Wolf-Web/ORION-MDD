import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ArticleDto } from '../models/article.model';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private apiUrl = 'http://localhost:8080/api/articles';

  constructor(private http: HttpClient) {}

  getAllArticles(): Observable<ArticleDto[]> {
    return this.http.get<ArticleDto[]>(this.apiUrl);
  }

  getArticleById(id: string): Observable<ArticleDto> {
    return this.http.get<ArticleDto>(`${this.apiUrl}/${id}`);
  }
}
