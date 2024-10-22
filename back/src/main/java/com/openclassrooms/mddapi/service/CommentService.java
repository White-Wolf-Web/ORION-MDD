package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentDto;

public interface CommentService {
    CommentDto postComment(CommentDto commentDto, String email);  // Méthode pour poster un commentaire
}
