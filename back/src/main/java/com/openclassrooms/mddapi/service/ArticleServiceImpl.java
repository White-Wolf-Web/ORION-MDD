package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleCreationDTO;
import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;// Interface pour accéder aux articles dans la base de données.
    private final TopicRepository topicRepository;    // Interface pour accéder aux thèmes dans la base de données.
    private final UserService userService;            // Service pour gérer les utilisateurs et leurs actions.

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, TopicRepository topicRepository, UserService userService) {
        this.articleRepository = articleRepository;
        this.topicRepository = topicRepository;
        this.userService = userService;
    }


    // Récupère tous les articles depuis la base de données.
    @Override
    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAll().stream() // Transforme les articles en une liste de streams.
                .map(article -> new ArticleDTO(     // Convertit chaque article en un ArticleDTO.
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getAuthor().getUsername(),
                article.getTopic().getName(),
                article.getCreatedAt()
        )).toList();                                // Retourne la liste des DTO d'articles.
    }


    // Récupère un article spécifique par son ID.
    @Override
    public ArticleDTO getArticleById(Long id) {           // Recherche l'article dans la base de données.
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article non trouvé"));
        return new ArticleDTO(                            // Crée un DTO pour retourner les données de l'article.
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getAuthor().getUsername(),
                article.getTopic().getName(),
                article.getCreatedAt()
        );
    }

    @Override
    public Article createArticle(ArticleCreationDTO articleCreationDTO, User author) {
        Long topicId = articleCreationDTO.getTopicId();        // Récupère l'ID du thème choisi pour l'article.

        // Log pour vérifier l'ID du thème et l'utilisateur
        logger.info("Tentative de création d'article par {} pour le thème {}", author.getEmail(), topicId);

        // Vérifiez si l'utilisateur est abonné au thème spécifié
        if (!userService.isUserSubscribedToTopic(topicId)) {
            throw new IllegalArgumentException("Vous devez être abonné à ce thème pour y créer un article.");
        }

        // Recherche le thème dans la base de données par son ID.
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Thème non trouvé"));

        // Crée un nouvel article en initialisant ses propriétés.
        Article article = new Article();
        article.setTitle(articleCreationDTO.getTitle());
        article.setContent(articleCreationDTO.getContent());
        article.setAuthor(author);
        article.setTopic(topic);


        // Enregistre l'article dans la base de données et le retourne.
        return articleRepository.save(article);
    }
}
