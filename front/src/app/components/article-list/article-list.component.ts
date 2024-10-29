import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArticleService } from '../../services/article.service';
import { ArticleDto } from '../../models/article.model';
import { ArticleCardComponent } from '../article-card/article-card.component';
import { ButtonComponent } from '../button/button.component';



@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  imports: [ArticleCardComponent, ButtonComponent, CommonModule],
  standalone: true,  
 
})
export class ArticleListComponent implements OnInit {
  articles: ArticleDto[] = []; 
  isAscending: boolean = true;

  constructor(private articleService: ArticleService) {}

  ngOnInit() {
    this.articleService.getAllArticles().subscribe((data: ArticleDto[]) => {
      this.articles = data;
    });
  }

  sortArticles(isAscending: boolean) {
    this.articles.sort((a, b) => {
      return isAscending
        ? a.title.localeCompare(b.title)
        : b.title.localeCompare(a.title);
    });
  }

  @Output() sort = new EventEmitter<boolean>();

  onSort() {
    this.isAscending = !this.isAscending;
    this.sort.emit(this.isAscending); 
  }

}
