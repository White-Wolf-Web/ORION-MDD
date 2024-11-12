import { Component, Input, OnInit } from '@angular/core';
import { CommentService } from '../../services/comment.service';
import { CommentDto } from '../../models/comment.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-comment-list',
  standalone: true, 
  imports: [CommonModule],
  templateUrl: './comment-list.component.html',
})
export class CommentListComponent implements OnInit {
  @Input() articleId!: number; // Définir `articleId` comme une propriété d'entrée
  comments: CommentDto[] = []; 

  constructor(private commentService: CommentService) {}

  ngOnInit() {
    if (this.articleId) {
      // Convertir `articleId` en chaîne de caractères
      this.commentService.getCommentsByArticleId(this.articleId.toString()).subscribe({
        next: (data: CommentDto[]) => {
          this.comments = data;
        },
        error: (error) => {
          console.error('Erreur lors de la récupération des commentaires:', error);
        },
      });
    } else {
      console.error("L'identifiant de l'article n'est pas fourni.");
    }
  }
}  
