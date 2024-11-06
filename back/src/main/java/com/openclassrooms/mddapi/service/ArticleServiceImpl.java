package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.dto.ArticleCreationDTO;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, TopicRepository topicRepository) {
        this.articleRepository = articleRepository;
        this.topicRepository = topicRepository;
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
        Article article = new Article();
        article.setTitle(articleCreationDTO.getTitle());
        article.setContent(articleCreationDTO.getContent());
        article.setAuthor(author);
        article.setCreatedAt(LocalDateTime.now());

        Topic topic = topicRepository.findById(articleCreationDTO.getTopicId())
                .orElseThrow(() -> new IllegalArgumentException("Thème non trouvé"));
        article.setTopic(topic);

        return articleRepository.save(article);
    }
}
