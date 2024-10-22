package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CommentDto postComment(CommentDto commentDto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Article article = articleRepository.findById(commentDto.getArticleId())
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setArticle(article);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);

        return convertToCommentDto(savedComment);
    }

    // Conversion de Comment en CommentDto
    private CommentDto convertToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setAuthorUsername(comment.getUser().getUsername());
        commentDto.setCreatedAt(comment.getCreatedAt().toString());
        return commentDto;
    }
}
