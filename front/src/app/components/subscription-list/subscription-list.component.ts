import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubscriptionService } from '../../services/subscription.service';
import { SubscriptionDto } from '../../models/subscription.model';
import { SubcriptionCardComponent } from '../subcription-card/subcription-card.component';

@Component({
  selector: 'app-subscription-list',
  standalone: true,
  imports: [SubcriptionCardComponent, CommonModule],
  templateUrl: './subscription-list.component.html'
})
export class SubscriptionListComponent implements OnInit {
  subscriptions: SubscriptionDto[] = [];

  constructor(private subscriptionService: SubscriptionService) {}

  ngOnInit() {
    this.subscriptionService.getAllSubscriptions().subscribe((data: SubscriptionDto[]) => {
      this.subscriptions = data;
    });
  }
}

