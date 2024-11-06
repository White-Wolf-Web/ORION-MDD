package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.CommentCreationDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getAllComments(Long articleId);
    void addComment(Long articleId, CommentCreationDTO commentDTO);
}
