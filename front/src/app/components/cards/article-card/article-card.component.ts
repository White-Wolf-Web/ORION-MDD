import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ShortDatePipe } from 'src/app/pipes/short-date.pipe';

@Component({
  selector: 'app-article-card',
  standalone: true,
  imports: [ShortDatePipe],
  templateUrl: './article-card.component.html',
  styleUrls: ['./article-card.component.scss'],
})
export class ArticleCardComponent {
  @Input() title!: string;
  @Input() date!: string;
  @Input() author!: string;
  @Input() content!: string;
  @Input() articleId?: number;
  @Input() topicName!: string;


  // Constructeur pour injecter les services nécessaires au composant.
  constructor(private router: Router) {}

  navigateToDetails() {
    if (this.articleId) {
      this.router.navigate([`/articles/${this.articleId}`]);
    } else {
      console.error('Article ID is undefined');
    }
  }
}

  /**
   * Propriétés d'entrée (Input) pour recevoir des données d'un composant parent.
   * @Input `title`: Représente le titre de l'article.
   * Exemple : `<app-article-card [title]="'Mon article'"></app-article-card>`
   */