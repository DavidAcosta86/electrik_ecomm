package com.electrik.electrik_ecomm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

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

    // Image of the article, stored in the db as a String
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

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