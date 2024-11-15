import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubscriptionListComponent } from './subscription-list/subscription-list.component';
import { SubscriptionRoutingModule } from './subscription-routing.module';

@NgModule({
  imports: [CommonModule, SubscriptionRoutingModule, SubscriptionListComponent],
})
export class SubscriptionModule {}
