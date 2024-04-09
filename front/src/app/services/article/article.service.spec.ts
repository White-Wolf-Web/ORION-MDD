import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { ArticleService } from './article.service';
import { Article } from 'src/app/models/article.model';
import { ArticleComment } from 'src/app/models/articleComment.model';

describe('ArticleService', () => {
  let service: ArticleService;
  let httpController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ArticleService],
    });

    service = TestBed.inject(ArticleService);
    httpController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpController.verify();
  });

  it('should retrieve all articles', () => {
    const dummyArticles: Article[] = [
      {
        id: '1',
        title: 'Test Article 1',
        date: '2020-01-01',
        author: 'Author 1',
        theme: 'Topic 1',
        content: 'Content 1',
      },
      {
        id: '2',
        title: 'Test Article 2',
        date: '2020-01-02',
        author: 'Author 2',
        theme: 'Topic 2',
        content: 'Content 2',
      },
    ];

    service.getArticles().subscribe((articles) => {
      expect(articles.length).toBe(2);
      expect(articles).toEqual(dummyArticles);
    });

    const req = httpController.expectOne(service['apiUrl']);
    expect(req.request.method).toBe('GET');
    req.flush(dummyArticles);
  });

  it('should find an article by ID', () => {
    const dummyArticle: Article = {
      id: '1',
      title: 'Test Article',
      date: '2020-01-01',
      author: 'Author',
      theme: 'Topic',
      content: 'Content',
    };

    service.getArticleById('1').subscribe((article) => {
      expect(article).toEqual(dummyArticle);
    });

    const req = httpController.expectOne(`${service['apiUrl']}/1`);
    expect(req.request.method).toBe('GET');
    req.flush(dummyArticle);
  });

  it('should retrieve comments for an article', () => {
    const dummyComments: ArticleComment[] = [
      {
        id: '1',
        content: 'Comment 1',
        date: new Date('2020-01-01'),
        username: 'User 1',
      },
      {
        id: '2',
        content: 'Comment 2',
        date: new Date('2020-01-02'),
        username: 'User 2',
      },
    ];

    service.getCommentsByArticleId('1').subscribe((comments) => {
      expect(comments.length).toBe(2);
      expect(comments).toEqual(dummyComments);
    });

    const req = httpController.expectOne(
      `${service['apiUrl']}/articles/1/comments`
    );
    expect(req.request.method).toBe('GET');
    req.flush(dummyComments);
  });

  it('should post a new comment to an article', () => {
    const newComment: ArticleComment = {
      id: '3',
      content: 'New comment',
      date: new Date(),
      username: 'New User',
    };

    service
      .addCommentToArticle('1', { content: 'New comment' })
      .subscribe((comment) => {
        expect(comment).toEqual(newComment);
      });

    const req = httpController.expectOne(
      `${service['apiUrl']}/articles/1/comments`
    );
    expect(req.request.method).toBe('POST');
    req.flush(newComment);
  });
});
