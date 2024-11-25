import { Component, OnInit, OnDestroy } from '@angular/core';
import { ArticleService } from 'src/app/services/article.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from 'src/app/components/button/button.component'; 
import { BackArrowComponent } from 'src/app/components/back-arrow/back-arrow.component';
import { SubscriptionService } from 'src/app/services/subscription.service'; 
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-article-create',
  standalone: true,
  imports: [FormsModule, CommonModule, ButtonComponent, BackArrowComponent],
  templateUrl: './article-create.component.html',
  styleUrls: ['./article-create.component.scss'],
})
export class ArticleCreateComponent implements OnInit, OnDestroy {
  isSmallScreen = window.innerWidth < 768;

   // Récupère le nom d'utilisateur stocké localement pour l'utiliser comme auteur de l'article.
  article = {
    title: '',
    content: '',
    topicId: 1,
    authorUsername: localStorage.getItem('username') || '', 
  };
  topics: { id: number; name: string }[] = [];        // Liste des sujets (topics) que l'utilisateur peut sélectionner pour son article.
  private subscriptionsList = new Subscription();     // Une "boîte" pour garder une trace des abonnements 

  constructor(
    private articleService: ArticleService,
    private subscriptionService: SubscriptionService,
    private router: Router
  ) {}

  ngOnInit() {

    const token = localStorage.getItem('token');
    if (!token) {
      alert('Token non disponible. Veuillez vous connecter.');
      this.router.navigate(['/login']);
    }
    
    // Récupère les sujets (topics) auxquels l'utilisateur est abonné.
    this.subscriptionService.getUserSubscriptions().subscribe((data: { id: number; name: string }[]) => {
      this.topics = data;
    });
  }

  // Lorsqu'on clique sur le bouton "Créer l'article".
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
  ngOnDestroy() {
    this.subscriptionsList.unsubscribe(); // Annuler tous les abonnements
    console.log('Tous les abonnements dans ArticleCreateComponent ont été annulés.');
  }
}
