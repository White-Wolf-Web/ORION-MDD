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

    private final ArticleRepository articleRepository;
    private final TopicRepository topicRepository;
    private final UserService userService;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, TopicRepository topicRepository, UserService userService) {
        this.articleRepository = articleRepository;
        this.topicRepository = topicRepository;
        this.userService = userService;
    }

    @Override
    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAll().stream().map(article -> new ArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getAuthor().getUsername(),
                article.getTopic().getName(),
                article.getCreatedAt()
        )).toList();
    }

    @Override
    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article non trouvé"));
        return new ArticleDTO(
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
        Long topicId = articleCreationDTO.getTopicId();

        // Log pour vérifier l'ID du thème et l'utilisateur
        logger.info("Tentative de création d'article par {} pour le thème {}", author.getEmail(), topicId);

        // Vérifiez si l'utilisateur est abonné au thème spécifié
        if (!userService.isUserSubscribedToTopic(topicId)) {
            throw new IllegalArgumentException("Vous devez être abonné à ce thème pour y créer un article.");
        }

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Thème non trouvé"));

        Article article = new Article();
        article.setTitle(articleCreationDTO.getTitle());
        article.setContent(articleCreationDTO.getContent());
        article.setAuthor(author);
        article.setTopic(topic);

        return articleRepository.save(article);
    }
}
