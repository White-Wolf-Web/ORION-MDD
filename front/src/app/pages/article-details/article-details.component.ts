import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Article } from 'src/app/models/article.model';
import { ArticleComment } from 'src/app/models/articleComment.model';
import { ArticleService } from 'src/app/services/article/article.service';

@Component({
  selector: 'app-article-details',
  templateUrl: './article-details.component.html',
  styleUrls: ['./article-details.component.scss'],
})
export class ArticleDetailsComponent implements OnInit {
  articleId: string = '';
  userInput: string = '';
  article: Article | null = null;
  comments: ArticleComment[] = [];

  constructor(
    private articleService: ArticleService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      console.error('Article ID is missing in the route');
      this.router.navigate(['/not-found']);
      return;
    }

    this.articleId = id;
    this.loadArticle();
    this.loadArticleComments();
  }

  loadArticle(): void {
    this.articleService.getArticleById(this.articleId).subscribe({
      next: (article) => {
        this.article = article;
      },
      error: (err) => {
        console.error("Erreur lors du chargement de l'article", err);
        this.router.navigate(['/not-found']);
      },
    });
  }

  loadArticleComments(): void {
    this.articleService
      .getCommentsByArticleId(this.articleId)
      .subscribe({
        next: (comments) => {
         
          this.comments = comments.map((ArticleComment) => ({
            id: ArticleComment.id,
            username: ArticleComment.username,
            content: ArticleComment.content,
            date: new Date(ArticleComment.date), 
          }));
        },
        error: (err) => {
          console.error(
            'Erreur lors du chargement des ArticleCommentaires',
            err
          );
        },
      });
  }

  submitArticleComment(): void {
    if (!this.userInput.trim()) return;

    this.articleService
      .addCommentToArticle(this.articleId, { content: this.userInput })
      .subscribe({
        next: () => {
          this.loadArticleComments();
          this.userInput = '';
        },
        error: (err) =>
          console.error("Erreur lors de l'ajout du ArticleCommentaire", err),
      });
  }
}
