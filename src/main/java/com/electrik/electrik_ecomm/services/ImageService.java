package com.electrik.electrik_ecomm.services;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.electrik.electrik_ecomm.entities.Image;
import com.electrik.electrik_ecomm.exceptions.MyException;
import com.electrik.electrik_ecomm.repositories.ImageRepository;

import jakarta.transaction.Transactional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Transactional
    public Image SaveImage(MultipartFile file) throws MyException {
        if (file == null) {
            throw new MyException("File cannot be null");
        }

        try {
            Image image = new Image();
            image.setMime(file.getContentType());
            image.setContent(file.getBytes());
            return imageRepository.save(image);
        } catch (IOException e) {
            throw new MyException("Error processing file: " + e.getMessage());
        }
    }

    @Transactional
    public Image modifyImage(UUID imageId, MultipartFile file) throws MyException {

        if (file == null) {
            throw new MyException("File cannot be null");
        }

        try {
            Image imgToModify = imageRepository.findById(imageId).get();
            imgToModify.setMime(file.getContentType());
            imgToModify.setContent(file.getBytes());
            return imageRepository.save(imgToModify);
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }

    }

    @Transactional
    public void deleteImage(UUID imageId) throws MyException {
        Optional<Image> imageToDel = imageRepository.findById(imageId);
        if (imageToDel.isPresent()) {
            imageRepository.delete(imageToDel.get());
        } else {
            throw new MyException("Image not found with ID: " + imageId);
        }
    }
}
