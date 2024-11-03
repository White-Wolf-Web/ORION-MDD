import { Component } from '@angular/core';
import { ArticleService } from '../../services/article.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../button/button.component';
import { BackArrowComponent } from '../back-arrow/back-arrow.component';

@Component({
  selector: 'app-article-create',
  standalone: true,
  imports: [FormsModule, CommonModule, ButtonComponent, BackArrowComponent],
  templateUrl: './article-create.component.html',
  styleUrl: './article-create.component.scss'
})
export class ArticleCreateComponent {
  isSmallScreen = window.innerWidth < 768;
  article = {
    title: '',
    content: '',
    authorUsername: '',
  };

  constructor(private articleService: ArticleService, private router: Router) {}

  onSubmit() {
    this.articleService.createArticle(this.article).subscribe(() => {
      this.router.navigate(['/articles']); 
      console.log('Article créé avec succès');
    });
  }

  
}