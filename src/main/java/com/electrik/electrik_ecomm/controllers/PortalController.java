package com.electrik.electrik_ecomm.controllers;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.electrik.electrik_ecomm.exceptions.MyException;
import com.electrik.electrik_ecomm.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/")
public class PortalController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("userregister")
    public String register() {
        return "userregister";
    }

    @PostMapping("register")
    public String userRegister(@RequestParam String name, String lastName, String email, String password,
            String password2, ModelMap model) {
        try {
            userService.CreateUser(email, name, lastName, password, password2);
            model.put("succes", "User created Succesfully!");
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
