import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

import { ArticleListComponent } from './pages/articles/article-list/article-list.component';
import { ArticleCreateComponent } from './pages/articles/article-create/article-create.component';
import { ArticleDetailsComponent } from './pages/articles/article-details/article-details.component';
import { SubscriptionListComponent } from './pages/topics/subscription-list/subscription-list.component';
import { SubscriptionDetailsComponent } from './components/subscription-details/subscription-details.component';
import { CommentListComponent } from './components/comment-list/comment-list.component';

import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { MeComponent } from './pages/me/me.component';

import { HeaderComponent } from './components/header/header.component';
import { ButtonComponent } from './components/button/button.component';

import { ArticleService } from './services/article.service';
import { CommentService } from './services/comment.service';
import { SubscriptionService } from './services/subscription.service';
import { UserService } from './services/user.service';

import { ShortDatePipe } from './pipes/short-date.pipe';
@NgModule({
  declarations: [AppComponent, SubscriptionDetailsComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    CommonModule,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    MeComponent,
    HeaderComponent,
    HttpClientModule,
    ButtonComponent,
    ArticleListComponent,
    ArticleDetailsComponent,
    ArticleCreateComponent,
    CommentListComponent,
    ShortDatePipe,
  ],

  providers: [ArticleService, CommentService, SubscriptionService, UserService],
  bootstrap: [AppComponent],
})
export class AppModule {}
