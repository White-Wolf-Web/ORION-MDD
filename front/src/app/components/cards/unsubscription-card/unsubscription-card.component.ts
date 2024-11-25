import { Component, Input, Output, EventEmitter } from '@angular/core';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { ButtonComponent } from '../../button/button.component';

@Component({
  selector: 'app-unsubscription-card',
  standalone: true,
  imports: [ButtonComponent],
  templateUrl: './unsubscription-card.component.html',
  styleUrls: ['./unsubscription-card.component.scss'],
})
export class UnsubscriptionCardComponent {
  @Input() title!: string;
  @Input() content!: string;
  @Input() subscriptionId!: number;
  @Output() unsubscribed = new EventEmitter<number>(); // Emettre l'ID du désabonnement

  constructor(private subscriptionService: SubscriptionService) {}

  onUnsubscribe() {
    this.subscriptionService.unsubscribeFromTopic(this.subscriptionId).subscribe(
      (response) => {
        alert(response.message); 
        this.unsubscribed.emit(this.subscriptionId);
      },
      (error) => {
        console.error('Échec du désabonnement:', error);
      }
    );
    
    
  }
}
