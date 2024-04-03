import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ArticleComponent } from './article.component';
import { ArticleService } from 'src/app/services/article/article.service';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';

describe('ArticleComponent', () => {
  let component: ArticleComponent;
  let fixture: ComponentFixture<ArticleComponent>;
  let articleServiceMock: any;
  let router: any;

  beforeEach(async () => {
    articleServiceMock = jasmine.createSpyObj('ArticleService', [
      'getArticles',
    ]);
    await TestBed.configureTestingModule({
      declarations: [ArticleComponent],
      imports: [RouterTestingModule],
      providers: [{ provide: ArticleService, useValue: articleServiceMock }],
    }).compileComponents();

    fixture = TestBed.createComponent(ArticleComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(RouterTestingModule);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load articles on init', () => {
    const dummyArticles = [
      { id: '1', title: 'Test Article', date: '2022-01-01' },
    ];
    articleServiceMock.getArticles.and.returnValue(of(dummyArticles));

    fixture.detectChanges();

    expect(articleServiceMock.getArticles).toHaveBeenCalled();
    expect(component.articles.length).toBe(1);
    expect(component.articles).toEqual(dummyArticles);
  });

  it('should handle error when loading articles fails', () => {
    articleServiceMock.getArticles.and.returnValue(
      throwError(() => new Error('Error loading articles'))
    );

    spyOn(console, 'error');
    fixture.detectChanges();

    expect(articleServiceMock.getArticles).toHaveBeenCalled();
    expect(console.error).toHaveBeenCalledWith(
      'Erreur lors du chargement des articles',
      jasmine.anything()
    );
  });

  it('should navigate to new article page on goToNewArticle call', () => {
    const router = TestBed.inject(Router);
    spyOn(router, 'navigate');

    component.goToNewArticle();

    expect(router.navigate).toHaveBeenCalledWith(['/new-article']);
  });

  it('should sort articles by date', () => {
    component.articles = [
      { id: '1', title: 'Article 1', date: '2022-01-02' },
      { id: '2', title: 'Article 2', date: '2022-01-01' },
    ];

    component.sort();

    expect(component.articles[0].id).toBe('1');
    expect(component.articles[1].id).toBe('2');
  });
});
