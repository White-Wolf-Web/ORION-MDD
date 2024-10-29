import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-article-card',
  standalone: true,
  imports: [],
  templateUrl: './article-card.component.html',
  styleUrl: './article-card.component.scss'
})
export class ArticleCardComponent {
  @Input() title!: string;
  @Input() date!: string;
  @Input() author!: string;
  @Input() content!: string;
}
