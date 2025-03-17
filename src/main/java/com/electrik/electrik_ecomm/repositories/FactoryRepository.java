package com.electrik.electrik_ecomm.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electrik.electrik_ecomm.entities.Factory;

public interface FactoryRepository extends JpaRepository<Factory, UUID> {

}
