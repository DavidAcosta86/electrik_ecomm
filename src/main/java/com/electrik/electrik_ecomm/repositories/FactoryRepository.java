package com.electrik.electrik_ecomm.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electrik.electrik_ecomm.entities.Factory;

import io.micrometer.common.lang.NonNull;

import org.springframework.stereotype.Repository;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, UUID> {
    @NonNull
    @Override
    Factory save(@NonNull Factory factory);

    Optional<Factory> findByFactoryName(String name);

    Optional<Factory> findById(UUID id);
}
