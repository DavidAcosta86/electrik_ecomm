package com.electrik.electrik_ecomm.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.electrik.electrik_ecomm.entities.Article;
import com.electrik.electrik_ecomm.entities.User;
import com.electrik.electrik_ecomm.exceptions.MyException;
import com.electrik.electrik_ecomm.services.ArticleService;
import com.electrik.electrik_ecomm.services.ImageService;
import com.electrik.electrik_ecomm.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity userImage(@PathVariable String id) throws MyException {
        try {

            User user = userService.getOne(id);
            byte[] userImg = user.getPicture().getContent();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity(userImg, headers, HttpStatus.OK);
        } catch (MyException e) {
            throw new MyException(e.getMessage());

        }
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<String> actualizeProfileId(@PathVariable String id,
            @RequestParam("file") MultipartFile file) {
        try {

            imageService.modifyImage(UUID.fromString(id), file);
            return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
        } catch (MyException e) {
            return new ResponseEntity<>("Image uploaded error", HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<byte[]> articleImage(@PathVariable String id) throws MyException {
        try {
            Article article = articleService.getOne(UUID.fromString(id));
            if (article != null && article.getImage() != null) {
                byte[] imageContent = article.getImage().getContent();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
