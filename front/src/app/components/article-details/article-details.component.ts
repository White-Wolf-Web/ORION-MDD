import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ArticleService } from '../../services/article.service';
import { ArticleDto } from '../../models/article.model'

@Component({
  selector: 'app-article-details',
  templateUrl: './article-details.component.html',
})
export class ArticleDetailsComponent implements OnInit {
  article: any;

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
