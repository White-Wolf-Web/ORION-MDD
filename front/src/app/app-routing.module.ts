import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { MeComponent } from './pages/me/me.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { TopicsComponent } from './pages/topics/topics.component';
import { ArticleComponent } from './pages/article/article.component';
import { ArticleDetailsComponent } from './pages/article-details/article-details.component';
import { NewArticleComponent } from './pages/new-article/new-article.component';
// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'me', component: MeComponent },
  { path: 'topics', component: TopicsComponent },
  { path: 'articles', component: ArticleComponent },
  { path: 'articles/:id', component: ArticleDetailsComponent },
  { path: 'new-article', component: NewArticleComponent },
  { path: '404', component: NotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
