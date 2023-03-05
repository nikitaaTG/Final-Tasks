package org.example.FinalProject.controllers;

import org.example.FinalProject.dto.CategoryDTO;
import org.example.FinalProject.mappers.CategoryMapper;
import org.example.FinalProject.models.CategoryEntity;
import org.example.FinalProject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/assortment")
public class CatgoryController {


    @Autowired
    private ProductService productService;

    @PostMapping("/addCategory")
    public String addCategory(String categoryName, Model model) {
        CategoryDTO category = CategoryMapper.INSTANCE.categoryEntityToDTO(productService.addCategory(categoryName));
        model.addAttribute("category", category);
        return "/category/viewCategory";
    }

    @GetMapping ("/addCategory")
    public String showCategory(){
        return "/category/addCategory";
    }

    @GetMapping ("/allCategories")
    public String viewAllCategories(Model model){
        List<CategoryDTO> categories = CategoryMapper.INSTANCE.listDTO(productService.getAllCategories());
        model.addAttribute("categories", productService.getAllCategories());
        return "/category/allCategories";
    }
}
