import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/auth/home/home.component';
import { AuthGuard } from './core/auth.guard';
import { UnAuthGuard } from './core/un-auth.gard';
import { NotFoundComponent } from './pages/not-found/not-found.component';

const routes: Routes = [
  { path: '', component: HomeComponent, 
    canActivate: [UnAuthGuard],
  },
  {
    path: 'auth',
    loadChildren: () =>
      import('./pages/auth/auth.module').then((m) => m.AuthModule),
    canActivate: [UnAuthGuard], // Empêche les utilisateurs connectés d'accéder à /auth
  },
  {
    path: 'articles',
    loadChildren: () =>
      import('./pages/articles/articles.module').then((m) => m.ArticlesModule),
    canActivate: [AuthGuard], // Empêche les utilisateurs non connectés
  },
  {
    path: 'topics',
    loadChildren: () =>
      import('./pages/topics/subscription.module').then(
        (m) => m.SubscriptionModule
      ),
    canActivate: [AuthGuard], // Empêche les utilisateurs non connectés
  },
  {
    path: 'me',
    loadChildren: () => import('./pages/me/me.module').then((m) => m.MeModule),
    canActivate: [AuthGuard], // Empêche les utilisateurs non connectés
  },
  { path: '**', component: NotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
