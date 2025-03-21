package com.electrik.electrik_ecomm.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.electrik.electrik_ecomm.exceptions.MyException;
import com.electrik.electrik_ecomm.services.FactoryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
            return "redirect:/factory/listfactories";
        } catch (MyException e) {
            model.put("error", e.getMessage());
            return "factory_form";
        }
    }

    @GetMapping("modify/{id}")
    public String searchModifyFactory(@PathVariable UUID id, ModelMap model) {
        System.out.println("ID recibido: " + id);
        model.put("factory", factoryService.getOne(id));
        return "factory_modify";
    }

    @PostMapping("modify/{id}")
    public String modifyFactory(@PathVariable UUID id, @RequestParam String factoryName, ModelMap model) {
        try {
            factoryService.ModifyFactory(id, factoryName);
            model.put("Success", "Factory created");
            return "redirect:/factory/listfactories";
        } catch (MyException e) {
            model.put("error", e.getMessage());
            return "redirect:/factory/listfactories";

        }
    }

    @PostMapping("delete/{id}")
    public String deleteFactory(@PathVariable UUID id, ModelMap model) {
        try {
            factoryService.DeleteFactory(id);
            model.put("success", "Item eliminado con éxito.");

        } catch (MyException e) {
            model.put("error", e.getMessage());
            return "listfactories";
        }
        return "redirect:/factory/listfactories"; // Redirigir después de eliminar
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
