import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ArticleDetailsComponent } from './article-details.component';
import { ArticleService } from 'src/app/services/article/article.service';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { By } from '@angular/platform-browser';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('ArticleDetailsComponent', () => {
  let component: ArticleDetailsComponent;
  let fixture: ComponentFixture<ArticleDetailsComponent>;
  let mockArticleService: any;
  let mockRouter: any;
  let mockActivatedRoute: any;

  beforeEach(async () => {
    mockArticleService = jasmine.createSpyObj('ArticleService', [
      'getArticleById',
      'getCommentsByArticleId',
      'addCommentToArticle',
    ]);
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);
    mockActivatedRoute = { snapshot: { paramMap: { get: () => '123' } } };

    await TestBed.configureTestingModule({
      declarations: [ArticleDetailsComponent],
      providers: [
        { provide: ArticleService, useValue: mockArticleService },
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
      ],
      schemas: [NO_ERRORS_SCHEMA], // Utiliser NO_ERRORS_SCHEMA pour ignorer les erreurs de schéma de template inconnu
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticleDetailsComponent);
    component = fixture.componentInstance;

    // Setup mocks
    mockArticleService.getArticleById.and.returnValue(
      of({
        id: '123',
        title: 'Test Article Title',
        date: '2024-01-01',
        author: 'Test Author',
        theme: 'Test Theme',
        content: 'Test content',
      })
    );

    mockArticleService.getCommentsByArticleId.and.returnValue(of([]));

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load article details on init', () => {
    expect(component.article).toBeTruthy();
    expect(component.article!.title).toEqual('Test Article Title');
    expect(mockArticleService.getArticleById).toHaveBeenCalledWith('123');
  });

  it('should submit comment and reload comments', () => {
    const newCommentContent = 'New comment';
    component.userInput = newCommentContent;
    mockArticleService.addCommentToArticle.and.returnValue(of({}));
    mockArticleService.getCommentsByArticleId.and.returnValue(of([{ id: '1', username: 'User', content: 'Comment', date: new Date() }]));

    component.submitArticleComment();

    expect(mockArticleService.addCommentToArticle).toHaveBeenCalledWith(component.articleId, { content: newCommentContent });
    expect(mockArticleService.getCommentsByArticleId).toHaveBeenCalledWith(component.articleId);
  });
});