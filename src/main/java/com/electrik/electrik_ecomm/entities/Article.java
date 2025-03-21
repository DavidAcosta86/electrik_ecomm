package com.electrik.electrik_ecomm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Article {

    // Auto-generated unique identifier for the article
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "article_id")
    private UUID articleId;

    // Internal unique sequential identification code for each article, an atomic
    // integer is created in the service
    @Column(name = "article_number")
    private Integer articleNumber;

    // Name of the article (Required field)
    @Column(nullable = false)
    private String articleName;

    // Description of the article (Required field)
    @Column(nullable = false, length = 500)
    private String articleDescription;

    // Manufacturer associated with the article
    @ManyToOne
    @JoinColumn(name = "factory_id", nullable = false)
    private Factory factory;
}