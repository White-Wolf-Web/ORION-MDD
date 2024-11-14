import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ShortDatePipe } from 'src/app/pipes/short-date.pipe';

@Component({
  selector: 'app-article-card',
  standalone: true,
  imports: [ShortDatePipe],
  templateUrl: './article-card.component.html',
  styleUrls: ['./article-card.component.scss']
})
export class ArticleCardComponent {
  @Input() title!: string;
  @Input() date!: string;
  @Input() author!: string;
  @Input() content!: string;
  @Input() articleId?: number;
  @Input() topicName!: string; 

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
