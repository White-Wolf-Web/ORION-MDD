import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubscriptionService } from '../../services/subscription.service';
import { SubscriptionDto } from '../../models/subscription.model';
import { SubcriptionCardComponent } from '../subcription-card/subcription-card.component';
import { HttpHeaders } from '@angular/common/http';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'app-subscription-list',
  standalone: true,
  imports: [SubcriptionCardComponent, CommonModule, ButtonComponent],
  templateUrl: './subscription-list.component.html'
})
export class SubscriptionListComponent implements OnInit {
  subscriptions: SubscriptionDto[] = [];

  constructor(private subscriptionService: SubscriptionService) {}

  ngOnInit() {
    this.getSubscriptions();
  }

  getSubscriptions() {
    const token = localStorage.getItem('token');

    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      
      this.subscriptionService.getAllSubscriptions(headers).subscribe(
        (data: SubscriptionDto[]) => {
          this.subscriptions = data;
        },
        (error) => {
          console.error('Error retrieving subscriptions:', error);
          if (error.status === 401) {
            alert('Vous devez être authentifié pour accéder à cette ressource.');
          }
        }
      );
    } else {
      alert('Token non disponible. Veuillez vous connecter.');
    }
  }
}
