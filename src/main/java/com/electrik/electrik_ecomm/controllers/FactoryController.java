package com.electrik.electrik_ecomm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.electrik.electrik_ecomm.exceptions.MyException;
import com.electrik.electrik_ecomm.services.FactoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/factory")
public class FactoryController {
    @Autowired
    private FactoryService factoryService;

    @GetMapping("/registration")
    public String CreateFactory() {
        return "factory_form";
    }

    @PostMapping("/register")
    public String FactoryRegistration(@RequestParam String factoryName, ModelMap model) {
        try {
            factoryService.CreateFactory(factoryName);
            model.put("success", "Factory created!"); // Fixed "succes" to "success"
            return "factorylist";
        } catch (MyException e) {
            model.put("error", e.getMessage());
            return "factory_form";
        }
    }

    @GetMapping("listfactories")
    public String ListFactories(ModelMap model) {
        try {

            model.addAttribute("factories", factoryService.ListAllFactories());
        } catch (MyException e) {
            // TODO: handle exception
        }
        return "factorylist";
    }

}
