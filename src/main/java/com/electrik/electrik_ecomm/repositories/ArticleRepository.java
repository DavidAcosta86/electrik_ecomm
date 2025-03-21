package com.electrik.electrik_ecomm.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.electrik.electrik_ecomm.entities.Article;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {

    @Query("SELECT COALESCE(MAX(a.articleNumber), 0) FROM Article a")
    Integer findMaxArticleNumber();

}
