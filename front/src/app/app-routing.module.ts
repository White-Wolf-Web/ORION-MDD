import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ArticleListComponent } from './components/article-list/article-list.component';
import { ArticleDetailsComponent } from './components/article-details/article-details.component';
import { SubscriptionListComponent } from './components/subscription-list/subscription-list.component';
import { SubscriptionDetailsComponent } from './components/subscription-details/subscription-details.component';
import { CommentListComponent } from './components/comment-list/comment-list.component';
import { ArticleCreateComponent } from './components/article-create/article-create.component';
import { MeComponent } from './pages/me/me.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'articles', component: ArticleListComponent },
  { path: 'articles/create', component: ArticleCreateComponent },
  { path: 'articles/:id', component: ArticleDetailsComponent },
  { path: 'subscriptions', component: SubscriptionListComponent },
  { path: 'subscriptions/:id', component: SubscriptionDetailsComponent },
  { path: 'comments', component: CommentListComponent },
  { path: 'me', component: MeComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
