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

    // ArticleServiceImpl.java
    @Override
    public ArticleDto saveArticle(ArticleDto articleDto, String username) {
        Subscription topic = subscriptionRepository.findById(articleDto.getTopicId())
                .orElseThrow(() -> new RuntimeException("Le sujet (topic) spécifié n'existe pas"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setAuthor(user);
        article.setTopic(topic);

        Article savedArticle = articleRepository.save(article);

        return convertToArticleDto(savedArticle); // Remplacez convertToDto par convertToArticleDto
    }


    @Override
    public ArticleDto findArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + id));
        return convertToArticleDto(article);
    }



    @Override
    public List<ArticleDto> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(this::convertToArticleDto)
                .collect(Collectors.toList());
    }


    // Méthode pour convertir Article en ArticleDto
    private ArticleDto convertToArticleDto(Article article) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(article.getId());
        articleDto.setTitle(article.getTitle());
        articleDto.setContent(article.getContent());
        articleDto.setAuthorUsername(article.getAuthor().getUsername());
        articleDto.setCreatedAt(article.getCreatedAt().toString());
        articleDto.setTopicId(article.getTopic().getId());
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
