import { Component, Input } from '@angular/core';
import { ButtonComponent } from '../../button/button.component';
import { SubscriptionService } from '../../../services/subscription.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-subcription-card',
  standalone: true,
  imports: [ButtonComponent],
  templateUrl: './subcription-card.component.html',
  styleUrls: ['./subcription-card.component.scss'],
})
export class SubcriptionCardComponent {
  @Input() title!: string;
  @Input() content!: string;
  @Input() subscriptionId!: number;

  constructor(private subscriptionService: SubscriptionService) {}

  onSubscribe() {
    this.subscriptionService.subscribeToTopic(this.subscriptionId).subscribe(
      () => {
        console.log('Abonnement réussi');
      },
      (error) => {
        console.log('Erreur capturée:', error);
        if (error.status === 400) {
          alert('Désolé, vous êtes déjà abonné à ce thème !');
        } else {
          console.error('Échec de l’abonnement:', error);
        }
      }
    );
  }
}
