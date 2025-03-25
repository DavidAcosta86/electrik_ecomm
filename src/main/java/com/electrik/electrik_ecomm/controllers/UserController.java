package com.electrik.electrik_ecomm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.electrik.electrik_ecomm.entities.Image;
import com.electrik.electrik_ecomm.entities.User;
import com.electrik.electrik_ecomm.exceptions.MyException;
import com.electrik.electrik_ecomm.repositories.UserRepository;
import com.electrik.electrik_ecomm.services.ImageService;
import com.electrik.electrik_ecomm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/list")
    public String listUsers(ModelMap model) {
        try {
            model.addAttribute("users", userService.ListAllUsers());
            return "userlist";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "start";
        }
    }

    @GetMapping("/edit/{id}")
    public String showProfileEditForm(@PathVariable String id, ModelMap model) {
        try {
            User user = userService.getOne(id);
            model.addAttribute("user", user);
            return "user_modify";
        } catch (MyException e) {
            model.put("error", e.getMessage());
            return "redirect:/user/list";
        }
    }

    @PostMapping("/update/{id}")
    public String updateUser(
            @PathVariable String id,
            @ModelAttribute("user") User user,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam String password,
            @RequestParam String password2,
            ModelMap model) {

        try {
            // Obtener el usuario existente para mantener datos no modificados
            User existingUser = userService.getOne(id);

            // Actualizar solo los campos modificables
            userService.ModifyUser(
                    user.getEmail(),
                    user.getName(),
                    user.getLastName(),
                    password,
                    password2);

            // Manejar la imagen si se proporcionó una nueva
            if (file != null && !file.isEmpty()) {
                Image image = imageService.SaveImage(file);
                existingUser.setPicture(image);
                userRepository.save(existingUser);
            }

            model.addAttribute("success", "User updated successfully!");
            return "redirect:/user/list";
        } catch (MyException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            return "user_modify";
        }
    }
}