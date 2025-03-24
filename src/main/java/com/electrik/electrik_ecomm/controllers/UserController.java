package com.electrik.electrik_ecomm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.electrik.electrik_ecomm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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
}
