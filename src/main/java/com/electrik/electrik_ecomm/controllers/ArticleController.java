package com.electrik.electrik_ecomm.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import com.electrik.electrik_ecomm.entities.Article;
import com.electrik.electrik_ecomm.entities.Factory;
import com.electrik.electrik_ecomm.exceptions.MyException;
import com.electrik.electrik_ecomm.services.ArticleService;
import com.electrik.electrik_ecomm.services.FactoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private FactoryService factoryService;

    @GetMapping("/list")
    public String ListArticles(ModelMap model) throws MyException {

        try {
            List<Article> articles = articleService.ListAllArticlesOrderByNumber();
            List<Factory> factories = factoryService.ListAllFactories();

            model.addAttribute("articles", articles);
            model.addAttribute("factories", factories);

            if (articles.isEmpty()) {
                model.addAttribute("info", "No articles found in the catalog. Add some articles to get started!");
            }

        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }

        return "articlelist";
    }

    @GetMapping("/create")
    public String createArticle(ModelMap model) throws MyException {
        model.addAttribute("factories", factoryService.ListAllFactories());
        return "articleform";
    }

    @PostMapping("/create")
    public String saveArticle(@RequestParam String articleName,
            @RequestParam String articleDescription,
            @RequestParam String factory_id,
            @RequestParam MultipartFile articleImage,
            ModelMap model) throws MyException {
        try {
            UUID factoryUUID = stringToUUID(factory_id);

            if (factoryUUID == null) {
                throw new MyException("Factory cannot be null!");
            }

            articleService.CreateArticle(articleName, articleDescription, factoryUUID, articleImage);
            model.put("success", "Article created successfully");
            return "redirect:/article/list";
        } catch (MyException e) {
            model.put("error", e.getMessage());
            model.addAttribute("factories", factoryService.ListAllFactories());
            return "redirect:/article/create";
        }
    }

    @GetMapping("modify/{id}")
    public String searchModifyArticle(@PathVariable UUID id, ModelMap model) throws MyException {
        model.put("article", articleService.getOne(id));
        model.addAttribute("factories", factoryService.ListAllFactories()); // Agregar esta línea
        return "article_modify";
    }

    @PostMapping("modify/{id}")
    public String modifyArticle(@PathVariable UUID id, @RequestParam String articleName,
            @RequestParam String articleDescription, @RequestParam String factory_id, ModelMap model) {
        try {
            UUID factoryUUID = stringToUUID(factory_id);
            if (factoryUUID == null) {
                throw new MyException("Factory cannot be null!");
            }
            articleService.ModifyArticle(id, articleName, articleDescription, factoryUUID);
            model.put("success", "Article has been modified");
            return "redirect:/article/list";

        } catch (MyException e) {
            model.put("error", e.getMessage());
            return "redirect:/article/list";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteArticle(@PathVariable UUID id, ModelMap model) {
        try {
            articleService.EliminateArticle(id);
            return "redirect:/article/list";
        } catch (Exception e) {
            return "list";

        }

    }

    private UUID stringToUUID(String id) {
        return (id != null && !id.trim().isEmpty()) ? UUID.fromString(id) // performs a conversion from UUID to string
                : null;
    }

}
