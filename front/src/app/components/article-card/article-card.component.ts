import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-article-card',
  standalone: true,
  imports: [],
  templateUrl: './article-card.component.html',
  styleUrls: ['./article-card.component.scss']
})
export class ArticleCardComponent {
  @Input() title!: string;
  @Input() date!: string;
  @Input() author!: string;
  @Input() content!: string;
  @Input() articleId?: number;

  constructor(private router: Router) {}

  navigateToDetails() {
    if (this.articleId) {
      console.log('Navigating to article with ID:', this.articleId);
      this.router.navigate([`/articles/${this.articleId}`]);
    } else {
      console.error('Article ID is undefined');
    }
  }
  
}
