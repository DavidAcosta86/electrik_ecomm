package com.electrik.electrik_ecomm.services;

import com.electrik.electrik_ecomm.entities.Article;
import com.electrik.electrik_ecomm.entities.Factory;
import com.electrik.electrik_ecomm.exceptions.MyException;
import com.electrik.electrik_ecomm.repositories.ArticleRepository;
import com.electrik.electrik_ecomm.repositories.FactoryRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private FactoryRepository factoryRepository;

    private AtomicInteger atomicInteger;

    @PostConstruct // I added this, so the repository is first injected and accesed later
    private void initAtomicInteger() {
        this.atomicInteger = new AtomicInteger(articleRepository.findMaxArticleNumber());
    }

    @Transactional
    public void CreateArticle(String articleName, String articleDescription, UUID factory_id) throws MyException {

        validateArticle(articleName, articleDescription, factory_id);
        Factory factory = factoryRepository.getReferenceById(factory_id);
        Article article = new Article();
        article.setArticleName(articleName);
        article.setArticleDescription(articleDescription);
        article.setFactory(factory);
        article.setArticleNumber(atomicInteger.incrementAndGet());

        articleRepository.save(article);

    }

    @Transactional
    public List<Article> ListAllArticles() throws MyException {
        List<Article> articles = new ArrayList<>();
        articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            throw new MyException("There is no article in the Catalog!");
        }
        return articles;

    }

    @Transactional
    public List<Article> ListAllArticlesOrderByNumber() {
        return articleRepository.findAllByOrderByArticleNumberAsc();
    }

    @Transactional
    public void EliminateArticle(UUID article_id) throws MyException {
        Optional<Article> article = articleRepository.findById(article_id);
        if (article.isPresent()) {
            articleRepository.delete(article.get());
        } else {
            throw new MyException("Article not found with ID: " + article_id);
        }

    }

    @Transactional
    public void ModifyArticle(UUID articleId, String articleName, String articleDescription, UUID factory_id)
            throws MyException {
        validateArticle(articleName, articleDescription, factory_id);
        Optional<Article> articleToModify = articleRepository.findById(articleId);
        Optional<Factory> factory = factoryRepository.findById(factory_id);

        if (articleToModify.isEmpty()) {
            throw new MyException("There is no article to modify with the  given Id ");

        }

        if (factory.isEmpty()) {
            throw new MyException("Must be a valid Factory!");

        }

        Article article = articleToModify.get();
        article.setArticleName(articleName);
        article.setArticleDescription(articleDescription);
        article.setFactory(factory.get());

        articleRepository.save(article);

    }

    @Transactional
    public Article getOne(UUID articleId) {
        return articleRepository.findById(articleId).orElse(null);
    }

    public void validateArticle(String articleName, String articleDescription, UUID factory_id) throws MyException {

        if ((articleName == null) || articleName.trim().isEmpty()) {
            throw new MyException("The name cannot be a blank or a null");
        }

        if ((articleDescription == null) || articleDescription.trim().isEmpty()) {
            throw new MyException("The description field  cannot  be a blank or a null");
        }

        if (factory_id == null) {
            throw new MyException("The Factory Id cannot be null");

        }

    }
}
