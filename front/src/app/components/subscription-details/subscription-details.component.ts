import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SubscriptionService } from '../../services/subscription.service';
import { SubscriptionDto } from '../../models/subscription.model'
@Component({
  selector: 'app-subscription-details',
  templateUrl: './subscription-details.component.html',
})
export class SubscriptionDetailsComponent implements OnInit {
  subscription: any;

  constructor(private route: ActivatedRoute, private subscriptionService: SubscriptionService) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.subscriptionService.getSubscriptionById(id).subscribe((data: SubscriptionDto) => {
        this.subscription = data;
      });
    } else {
      console.error('Subscription ID is null');
    }
  }
  
}
