package com.electrik.electrik_ecomm.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electrik.electrik_ecomm.entities.Image;

public interface ImageRepository extends JpaRepository<Image, UUID> {

}
