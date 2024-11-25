import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms'; 
import { ArticleService } from 'src/app/services/article.service'; 
import { CommentService } from 'src/app/services/comment.service'; 
import { ArticleDto } from 'src/app/models/article.model'; 
import { BackArrowComponent } from 'src/app/components/back-arrow/back-arrow.component'; 
import { CommentListComponent } from 'src/app/components/comment-list/comment-list.component'; 
import { CommentDto } from 'src/app/models/comment.model';
import { Router } from '@angular/router';
import { ShortDatePipe } from 'src/app/pipes/short-date.pipe';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-article-details',
  standalone: true,
  imports: [BackArrowComponent, CommonModule, FormsModule,  CommentListComponent, ShortDatePipe], 
  templateUrl: './article-details.component.html',
})
export class ArticleDetailsComponent implements OnInit, OnDestroy {
  article: ArticleDto | undefined;               // Variable pour stocker les informations de l'article affiché.
  articleId!: number;                            // Identifiant unique de l'article récupéré depuis l'URL.
  comments: CommentDto[] = [];                   // Liste des commentaires associés à l'article.
  newComment: string = '';                       // Contenu du nouveau commentaire que l'utilisateur veut ajouter.
  private subscriptionsList = new Subscription();// Gestionnaire pour suivre et annuler les abonnements

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
  this.articleService.getArticleById(id).subscribe({
    next: (data: ArticleDto) => {
      if (data && data.id !== undefined) {
        this.article = data;
        this.articleId = data.id;
      } else {
        console.error('Article introuvable ou sans ID valide.');
        this.router.navigate(['/404']); // Redirection si l'article est absent ou invalide
      }
    },
    error: (error) => {
      console.error('Erreur lors de la récupération de l\'article :', error);
      this.router.navigate(['/404']); // Redirection en cas d'erreur API
    },
  });
} else {
  console.error('ID d\'article manquant dans la route.');
  this.router.navigate(['/404']); // Redirection si aucun ID dans la route
}
  }

  // Charge les commentaires liés à l'article.
  loadComments(articleId: string) {
    this.commentService.getCommentsByArticleId(articleId).subscribe({
      next: (data) => (this.comments = data),
      error: (error) => console.error('Erreur lors de la récupération des commentaires:', error),
    });
  }

  // Ajoute un commentaire à l'article.
  submitComment() {
    if (!this.newComment.trim()) return;

    const id = this.route.snapshot.paramMap.get('id');    // Récupère l'ID de l'article depuis l'URL.
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
  
  // Nettoyer les abonnements lors de la destruction du composant
  ngOnDestroy() {
    this.subscriptionsList.unsubscribe(); 
    console.log('Tous les abonnements dans ArticleDetailsComponent ont été annulés.');
  }
}
