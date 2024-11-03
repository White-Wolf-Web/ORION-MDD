import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ArticleService } from '../../services/article.service';
import { ArticleDto } from '../../models/article.model';
import { BackArrowComponent } from '../back-arrow/back-arrow.component';

@Component({
  selector: 'app-article-details',
  standalone: true,
  imports: [BackArrowComponent, CommonModule],
  templateUrl: './article-details.component.html',
})
export class ArticleDetailsComponent implements OnInit {
  article: ArticleDto | undefined;

  constructor(private route: ActivatedRoute, private articleService: ArticleService) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.articleService.getArticleById(id).subscribe((data: ArticleDto) => {
        this.article = data;
      });
    } else {
      console.error('Article ID is null');
    }
  }
}
