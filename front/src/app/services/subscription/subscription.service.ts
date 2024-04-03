import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Subscription } from 'src/app/models/subscription.model';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  constructor() {}

  // Cette fonction simule la récupération des abonnements depuis un backend
  getSubscriptionsForUser(): Observable<Subscription[]> {
    // Remplacez ceci par votre appel HTTP réel
    return of([
      { id: '1', title: 'Abonnement 1', description: 'Description de l\'abonnement 1' },
      { id: '2', title: 'Abonnement 2', description: 'Description de l\'abonnement 2' }
    ]);
  }
}
