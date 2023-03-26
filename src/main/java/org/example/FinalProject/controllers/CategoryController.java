package org.example.FinalProject.controllers;

import jakarta.validation.Valid;
import org.example.FinalProject.dto.CategoryDTO;
import org.example.FinalProject.dto.ProductDTO;
import org.example.FinalProject.mappers.CategoryMapper;
import org.example.FinalProject.mappers.ProductMapper;
import org.example.FinalProject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("categories", categories);
        return "/category/allCategories";
    }


    @GetMapping("/{id}/editCategory")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public String editProductCategory(Model model,
                                      @PathVariable("id") long id) {
        ProductDTO product = ProductMapper.INSTANCE.productEntityToDTO(productService.getProductById(id));
        model.addAttribute("product", product);
        model.addAttribute("prodTitle", product.getTitle());
        model.addAttribute("categories", CategoryMapper.INSTANCE.listDTO(productService.getAllCategories()));
        return "products/editCategory";
    }

    @PatchMapping("/{id}/category")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public String updateProductCategory(@ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult bindingResult,
                                        @PathVariable("id") long id, Model model) {
        model.addAttribute("categories", CategoryMapper.INSTANCE.listDTO(productService.getAllCategories()));
        if (bindingResult.hasErrors()) {
            productService.updateProductCategory(id, productDTO);
            return "redirect:/assortment/{id}";
        }
        return "redirect:/assortment/{id}";
    }
}
