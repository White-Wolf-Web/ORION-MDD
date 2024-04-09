import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { MeComponent } from './pages/me/me.component';
import { ArticleComponent } from './pages/article/article.component';
import { TopicsComponent } from './pages/topics/topics.component';
import { HeaderComponent } from './components/header/header.component';
import { NavComponent } from './components/nav/nav.component';
import { ArticleCardComponent } from './components/article-card/article-card.component';
import { TopicCardComponent } from './components/topic-card/topic-card.component';
import { ButtonComponent } from './components/button/button.component';
import { ArticleDetailsComponent } from './pages/article-details/article-details.component';
import { MaterialModule } from './shared/material/material.module';
import { ArrowBackComponent } from './components/arrow-back/arrow-back.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NewArticleComponent } from './pages/new-article/new-article.component';
import { AuthInputComponent } from './components/input/auth-input/auth-input.component';
import { GeneralInputComponent } from './components/input/general-input/general-input.component';
import { AuthInterceptor } from './core/interceptors/auth.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NotFoundComponent,
    RegisterComponent,
    LoginComponent,
    MeComponent,
    ArticleComponent,
    TopicsComponent,
    HeaderComponent,
    NavComponent,
    ArticleCardComponent,
    TopicCardComponent,
    ButtonComponent,
    ArticleDetailsComponent,
    ArrowBackComponent,
    NewArticleComponent,
    AuthInputComponent,
    GeneralInputComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
