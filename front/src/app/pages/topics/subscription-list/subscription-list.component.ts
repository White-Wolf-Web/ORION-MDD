import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubscriptionService } from '../../../services/subscription.service';
import { SubscriptionDto } from '../../../models/subscription.model';
import { SubcriptionCardComponent } from '../../../components/cards/subcription-card/subcription-card.component';
import { HttpHeaders } from '@angular/common/http';
import { ButtonComponent } from '../../../components/button/button.component';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-subscription-list',
  standalone: true,
  imports: [SubcriptionCardComponent, CommonModule, ButtonComponent],
  templateUrl: './subscription-list.component.html',
})
export class SubscriptionListComponent implements OnInit, OnDestroy  {
  subscriptions: SubscriptionDto[] = [];              // Tableau pour stocker les abonnements récupérés depuis le back-end.
  private subscriptionsList = new Subscription();     // Objet pour gérer les abonnements

  constructor(
    private subscriptionService: SubscriptionService,
    private router: Router
  ) {}

  // Appelle une méthode pour récupérer les abonnements des l'initialisation.
  ngOnInit() {
    this.getSubscriptions();
  }

   // Méthode pour récupérer tous les abonnements
  getSubscriptions() {
    const token = localStorage.getItem('token');

    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

      // Appelle le service pour récupérer les abonnements.
      this.subscriptionService.getAllSubscriptions().subscribe(
        (data: SubscriptionDto[]) => {
          this.subscriptions = data;
        },
        (error) => {
          console.error('Error retrieving subscriptions:', error);
          if (error.status === 401) {
            alert(
              'Vous devez être authentifié pour accéder à cette ressource.'
            );
          }
        }
      );
    } else {
      alert('Token non disponible. Veuillez vous connecter.');
      this.router.navigate(['/login']);
    }
  }

  // Nettoyer les abonnements lorsque le composant est détruit
  ngOnDestroy() {
    this.subscriptionsList.unsubscribe(); // Annuler tous les abonnements
    console.log('Tous les abonnements dans SubscriptionListComponent ont été annulés.');
  }
}
