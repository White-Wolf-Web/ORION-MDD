import { Component, OnInit } from '@angular/core';
import { CommentService } from '../../services/comment.service';
import { CommentDto } from '../../models/comment.model';

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
})
export class CommentListComponent implements OnInit {
  comments: CommentDto[] = []; 

  constructor(private commentService: CommentService) {}

  ngOnInit() {
    this.commentService.getComments().subscribe((data: CommentDto[]) => {
      this.comments = data;
    });
  }
}
