package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository; // Ajout du repository pour les sujets (topics)

    @Override
    public ArticleDto saveArticle(ArticleDto articleDto, String username) {
        // Récupérer l'utilisateur connecté
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            Article article = convertToArticle(articleDto);

            // Récupérer le sujet (topic) à partir du topicId fourni dans ArticleDto
            Optional<Subscription> topic = subscriptionRepository.findById(articleDto.getTopicId());
            if (topic.isPresent()) {
                article.setTopic(topic.get()); // Associer le sujet à l'article
            } else {
                throw new RuntimeException("Le sujet (topic) spécifié n'existe pas");
            }

            article.setAuthor(user.get());  // Associer l'utilisateur connecté comme auteur
            return convertToArticleDto(articleRepository.save(article));
        }
        throw new RuntimeException("Utilisateur non trouvé");
    }

    @Override
    public ArticleDto updateArticle(ArticleDto articleDto) {
        Article article = convertToArticle(articleDto);

        // Gérer l'association du topic lors de la mise à jour
        Optional<Subscription> topic = subscriptionRepository.findById(articleDto.getTopicId());
        if (topic.isPresent()) {
            article.setTopic(topic.get()); // Mettre à jour le sujet
        } else {
            throw new RuntimeException("Le sujet (topic) spécifié n'existe pas");
        }

        return convertToArticleDto(articleRepository.save(article));
    }

    @Override
    public ArticleDto findArticleById(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        return article != null ? convertToArticleDto(article) : null;
    }

    @Override
    public List<ArticleDto> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(this::convertToArticleDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findArticlesByAuthorId(Long authorId) {
        List<Article> articles = articleRepository.findByAuthorId(authorId);
        return articles.stream()
                .map(this::convertToArticleDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    // Méthode pour convertir Article en ArticleDto
    private ArticleDto convertToArticleDto(Article article) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(article.getId());
        articleDto.setTitle(article.getTitle());
        articleDto.setContent(article.getContent());
        articleDto.setAuthorUsername(article.getAuthor().getUsername());  // Associer l'auteur
        articleDto.setCreatedAt(article.getCreatedAt().toString());  // Conversion de la date en string
        articleDto.setTopicId(article.getTopic().getId());  // Ajouter l'id du sujet (topic)
        return articleDto;
    }

    // Méthode pour convertir ArticleDto en Article
    private Article convertToArticle(ArticleDto articleDto) {
        Article article = new Article();
        article.setId(articleDto.getId());
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        return article;
    }
}
