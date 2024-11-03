import { Component, Input } from '@angular/core';
import { ButtonComponent } from '../button/button.component';
import { SubscriptionService } from '../../services/subscription.service';

@Component({
  selector: 'app-subcription-card',
  standalone: true,
  imports: [ButtonComponent],
  templateUrl: './subcription-card.component.html',
  styleUrls: ['./subcription-card.component.scss']
})
export class SubcriptionCardComponent {
  @Input() title!: string;
  @Input() content!: string;
  @Input() subscriptionId!: number;

  constructor(private subscriptionService: SubscriptionService) {}

  onSubscribe() {
    this.subscriptionService.subscribeToTopic(this.subscriptionId).subscribe(
      () => {
        console.log('Subscription successful');
      },
      (error) => {
        console.error('Subscription failed:', error);
      }
    );
  }
}
