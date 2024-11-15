import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AuthGuard } from './core/auth.guard';

const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'auth',
    loadChildren: () =>
      import('./pages/auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: 'articles',
    loadChildren: () =>
      import('./pages/articles/articles.module').then((m) => m.ArticlesModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'topics',
    loadChildren: () =>
      import('./pages/topics/subscription.module').then(
        (m) => m.SubscriptionModule
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'me',
    loadChildren: () => import('./pages/me/me.module').then((m) => m.MeModule),
    canActivate: [AuthGuard],
  },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
