package com.electrik.electrik_ecomm.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electrik.electrik_ecomm.entities.Factory;

import io.micrometer.common.lang.NonNull;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, UUID> {
    @SuppressWarnings({ "null", "unchecked" })
    @NonNull
    @Override
    Factory save(@NonNull Factory factory);

    Optional<Factory> findByFactoryName(String name);

    @SuppressWarnings("null")
    Optional<Factory> findById(UUID id);
}
