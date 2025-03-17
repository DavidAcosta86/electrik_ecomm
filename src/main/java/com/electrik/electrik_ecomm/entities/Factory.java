package com.electrik.electrik_ecomm.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Factory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "factory_id")
    private UUID id;

    @OneToMany(mappedBy = "factory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles;

    @Column(name = "factory_name")
    private String factoryName;

}
