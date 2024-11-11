import { Component, OnInit } from '@angular/core';
import { ArticleService } from '../../services/article.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../components/button/button.component';
import { BackArrowComponent } from '../../components/back-arrow/back-arrow.component';
import { SubscriptionService } from 'src/app/services/subscription.service'; 

@Component({
  selector: 'app-article-create',
  standalone: true,
  imports: [FormsModule, CommonModule, ButtonComponent, BackArrowComponent],
  templateUrl: './article-create.component.html',
  styleUrls: ['./article-create.component.scss'],
})
export class ArticleCreateComponent implements OnInit {
  isSmallScreen = window.innerWidth < 768;
  article = {
    title: '',
    content: '',
    topicId: 1,
    authorUsername: localStorage.getItem('username') || '', 
  };
  topics: { id: number; name: string }[] = [];

  constructor(
    private articleService: ArticleService,
    private subscriptionService: SubscriptionService,
    private router: Router
  ) {}

  ngOnInit() {
    this.subscriptionService.getUserSubscriptions().subscribe((data: { id: number; name: string }[]) => {
      this.topics = data;
    });
  }

  onSubmit() {
    this.articleService.createArticle(this.article).subscribe(
      () => {
        this.router.navigate(['/articles']);
        console.log('Article créé avec succès');
      },
      (error) => {
        console.error('Erreur lors de la création de l’article :', error);
      }
    );
  }
}
