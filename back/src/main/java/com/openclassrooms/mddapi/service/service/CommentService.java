package com.openclassrooms.mddapi.service.service;

import com.openclassrooms.mddapi.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long postId, CommentDto commentDto);
    List<CommentDto> findAllCommentsByPostId(Long postId);
}

