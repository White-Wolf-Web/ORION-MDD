import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArticleListComponent } from './article-list/article-list.component'; 
import { ArticleDetailsComponent } from './article-details/article-details.component';
import { ArticleCreateComponent } from './article-create/article-create.component';

import { NotFoundComponent } from '../not-found/not-found.component';

const routes: Routes = [
  { path: '', component: ArticleListComponent }, 
  { path: 'create', component: ArticleCreateComponent }, 
  { path: ':id', component: ArticleDetailsComponent },
  { path: '**', component: NotFoundComponent }, 
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ArticlesRoutingModule {}
