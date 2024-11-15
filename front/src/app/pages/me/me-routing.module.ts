import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MeComponent } from './me.component';
import { SubscriptionListComponent } from '../topics/subscription-list/subscription-list.component';

const routes: Routes = [
  { path: '', component: MeComponent },
  { path: 'topics', component: SubscriptionListComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MeRoutingModule {}
