import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArticleService } from '../../services/article.service';
import { ArticleDto } from '../../models/article.model';
import { ArticleCardComponent } from '../../components/article-card/article-card.component';
import { ButtonComponent } from '../../components/button/button.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  imports: [ArticleCardComponent, ButtonComponent, CommonModule],
  standalone: true,
})
export class ArticleListComponent implements OnInit {
  articles: ArticleDto[] = [];
  isAscending: boolean = true;

  constructor(private articleService: ArticleService, private router: Router) {}

  ngOnInit() {
    this.articleService.getAllArticles().subscribe((data: ArticleDto[]) => {
      this.articles = data;
    });
  }

  // Rediriger vers la page de création d'article
  createArticle() {
    this.router.navigate(['articles/create']);
  }

  // Trier les articles par date
  sortArticles(isAscending: boolean) {
    this.articles.sort((a, b) => {
      const dateA = new Date(a.createdAt!).getTime();
      const dateB = new Date(b.createdAt!).getTime();
      return isAscending ? dateA - dateB : dateB - dateA;
    });
  }

  @Output() sort = new EventEmitter<boolean>();

  onSort() {
    this.isAscending = !this.isAscending;
    this.sort.emit(this.isAscending);
    this.sortArticles(this.isAscending); // Trier après chaque clic
  }
}
