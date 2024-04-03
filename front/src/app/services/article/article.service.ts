import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Article } from 'src/app/models/article.model';
import { ArticleComment } from 'src/app/models/articleComment.model';

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  private apiUrl = 'http://localhost:8080/api/service';

  constructor(private http: HttpClient) {}

  getArticles(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getArticleById(id: string): Observable<Article> {
    return this.http.get<Article>(`${this.apiUrl}/${id}`);
  }

  getCommentsByArticleId(articleId: string): Observable<ArticleComment[]> {
    return this.http.get<ArticleComment[]>(
      `${this.apiUrl}/articles/${articleId}/comments`
    );
  }

  addCommentToArticle(
    articleId: string,
    comment: { content: string }
  ): Observable<ArticleComment> {
    return this.http.post<ArticleComment>(
      `${this.apiUrl}/articles/${articleId}/comments`,
      comment
    );
  }
}
