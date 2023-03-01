package org.example.FinalProject.controllers;

import org.example.FinalProject.models.CategoryEntity;
import org.example.FinalProject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assortment")
public class CatgoryController {


    @Autowired
    private ProductService productService;

    @PostMapping("/addCategory")
    public String addCategory(String categoryName, Model model) {
        CategoryEntity categoryEntity = productService.addCategory(categoryName);
        model.addAttribute("category", categoryEntity);
        return "/category/viewCategory";
    }

    @GetMapping ("/addCategory")
    public String showCategory(){
        return "/category/addCategory";
    }

    @GetMapping ("/allCategories")
    public String viewAllCategories(Model model){
        model.addAttribute("categories", productService.getAllCategories());
        return "/category/allCategories";
    }
}
