import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms'; 
import { ArticleService } from '../../services/article.service';
import { CommentService } from '../../services/comment.service';
import { ArticleDto } from '../../models/article.model';
import { BackArrowComponent } from '../../components/back-arrow/back-arrow.component';
import { CommentListComponent } from '../../components/comment-list/comment-list.component'; 
import { CommentDto } from 'src/app/models/comment.model';
import { Router } from '@angular/router';
import { ShortDatePipe } from 'src/app/pipes/short-date.pipe';

@Component({
  selector: 'app-article-details',
  standalone: true,
  imports: [BackArrowComponent, CommonModule, FormsModule,  CommentListComponent, ShortDatePipe], 
  templateUrl: './article-details.component.html',
})
export class ArticleDetailsComponent implements OnInit {
  article: ArticleDto | undefined;
  articleId!: number; 
  comments: CommentDto[] = [];
  newComment: string = '';

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService,
    private commentService: CommentService,
    private router: Router
  ) {}

  ngOnInit() {
    const token = localStorage.getItem('token');
    if (!token) {
      alert('Token non disponible. Veuillez vous connecter.');
      this.router.navigate(['/login']);
    }

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.articleService.getArticleById(id).subscribe((data: ArticleDto) => {
        this.article = data;
        if (this.article.id !== undefined) {
          this.articleId = this.article.id; // Assigner l'ID à la propriété `articleId`
        }
      });
    } else {
      console.error('Article ID is null');
    }
  }

  loadComments(articleId: string) {
    this.commentService.getCommentsByArticleId(articleId).subscribe({
      next: (data) => (this.comments = data),
      error: (error) => console.error('Erreur lors de la récupération des commentaires:', error),
    });
  }

  submitComment() {
    if (!this.newComment.trim()) return;

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.commentService.addComment(id, this.newComment).subscribe({
        next: () => {
          this.loadComments(id);
          this.newComment = '';
        },
        error: (error) => console.error("Erreur lors de l'ajout du commentaire:", error),
      });
    }
  }
}
