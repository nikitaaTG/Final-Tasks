package org.example.FinalProject.controllers;

import org.example.FinalProject.dto.CategoryDTO;
import org.example.FinalProject.mappers.CategoryMapper;
import org.example.FinalProject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/assortment")
public class CategoryController {


    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/addCategory")
    public String addCategory(String categoryName, Model model) {
        CategoryDTO category = CategoryMapper.INSTANCE.categoryEntityToDTO(productService.addCategory(categoryName));
        model.addAttribute("category", category);
        return "/category/viewCategory";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/addCategory")
    public String showCategory() {
        return "/category/addCategory";
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @GetMapping("/allCategories")
    public String viewAllCategories(Model model) {
        List<CategoryDTO> categories = CategoryMapper.INSTANCE.listDTO(productService.getAllCategories());
        model.addAttribute("categories", productService.getAllCategories());
        return "/category/allCategories";
    }
}
