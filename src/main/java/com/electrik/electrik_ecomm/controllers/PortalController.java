package com.electrik.electrik_ecomm.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.electrik.electrik_ecomm.exceptions.MyException;
import com.electrik.electrik_ecomm.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/")
public class PortalController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("userregister")
    public String register() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
        return "userregister";
    }

    @PostMapping("/register")
    public String userRegister(@RequestParam String name, String lastName, MultipartFile file, String email,
            String password,
            String password2, ModelMap model) {
        System.out.println("despues de entrar al servicio");
        try {
            userService.CreateUser(email, name, lastName, file, password, password2);
            model.put("succes", "User created Succesfully!");
            System.out.println("llego acá");
            return "login";
        } catch (MyException e) {
            model.put("error", e.getMessage());
            model.put("name", name);
            model.put("lastName", lastName);
            return "userregister";
        }

    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model) {
        if (error != null) {
            model.put("error", "Invalid e-mail or password!");
        }
        return "login";
    }

    @PostMapping("/loginprocess")
    public String loginProcess() {
        return "redirect:/";
    }

    @GetMapping("/start")
    public String getMethodName() {
        return "start";
    }

}
