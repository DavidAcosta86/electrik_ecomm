package com.electrik.electrik_ecomm.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.electrik.electrik_ecomm.exceptions.MyException;
import com.electrik.electrik_ecomm.services.ArticleService;
import com.electrik.electrik_ecomm.services.FactoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private FactoryService factoryService;

    @GetMapping("/list")
    public String ListArticles(ModelMap model) throws MyException {
        model.addAttribute("articles", articleService.ListAllArticles());
        return "articlelist";
    }

    @GetMapping("/create")
    public String createArticle(ModelMap model) throws MyException {
        model.addAttribute("factories", factoryService.ListAllFactories());
        return "articleform";
    }

    @PostMapping("/create")
    public String saveArticle(@RequestParam String articleName, String articleDescription, String factory_id,
            ModelMap model) throws MyException {
        System.out.println(factory_id);
        try {
            UUID factoryUUID = (factory_id != null && !factory_id.trim().isEmpty()) ? UUID.fromString(factory_id)
                    : null;

            if (factoryUUID == null) {
                throw new MyException("Factory cannot be null!");
            }

            articleService.CreateArticle(articleName, articleDescription, factoryUUID);
            model.put("sucess", "Article created successfully");
            return "redirect:/article/list";
        } catch (MyException e) {
            model.put("error", e.getMessage());
            model.addAttribute("factories", factoryService.ListAllFactories());
            System.out.println(e.getMessage());
            return "redirect:/article/create";

        }
    }

    @PostMapping("/delete/{id}")
    public String deleteArticle(@PathVariable UUID id, ModelMap model) {
        try {
            articleService.EliminateArticle(id);
            return "redirect:/article/list";
        } catch (Exception e) {
            return "redirect:/article/list";

        }

    }

}
