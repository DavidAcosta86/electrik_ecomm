package com.electrik.electrik_ecomm.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electrik.electrik_ecomm.entities.Article;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {

    // @Query ("SELECT COALESCE(MAX(a.id), 0) FROM Articulo a")
    // public integer findMaxArticle;
}
