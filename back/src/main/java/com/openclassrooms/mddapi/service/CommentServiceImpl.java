package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentCreationDTO;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addComment(Long articleId, CommentCreationDTO commentDTO) {

        // Récupérer l'article correspondant à l'ID fourni. Si l'article n'existe pas, une exception est levée.
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article non trouvé"));

        User author = getCurrentUser();

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setArticle(article);
        comment.setAuthor(author);
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }

    @Override
    public List<CommentDTO> getCommentsByArticleId(Long articleId) {

        // Récupérer tous les commentaires associés à un article spécifique, triés par date de création.
        List<Comment> comments = commentRepository.findByArticleIdOrderByCreatedAtAsc(articleId);

        // Convertir chaque commentaire en objet DTO pour renvoyer uniquement les informations nécessaires.
        return comments.stream().map(comment -> new CommentDTO(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor().getUsername(),
                comment.getCreatedAt()
        )).collect(Collectors.toList());
    }

    // Méthode privée pour récupérer l'utilisateur actuellement connecté.
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        return userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'email : " + currentUserEmail));
    }

}
