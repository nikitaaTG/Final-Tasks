package org.example.FinalProject.controllers;

import jakarta.validation.Valid;
//import org.example.FinalProject.DAO.ProductDAO;
import org.example.FinalProject.dto.ProductDTO;
import org.example.FinalProject.models.CategoryEntity;
import org.example.FinalProject.models.ProductEntity;
import org.example.FinalProject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assortment")
public class AssortmentController {

//    @Autowired
//    private ProductDAO productDAO;

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

    @GetMapping()
    public String index(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.ASC, value = 2) Pageable pageable) {
        Page<ProductEntity> pages = productService.listProducts(pageable);

        model.addAttribute("products", pages);
    return "products/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        ProductEntity product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("prodTitle", product.getTitle());
    return "products/show";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("categories", productService.getAllCategories());
        return "products/addProduct";
    }

    @PostMapping()
    public String create(@ModelAttribute("product") @Valid ProductDTO product,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "products/addProduct"; }
        productService.saveProduct(product);
        return "redirect:/assortment";
    }

    @GetMapping("/{id}/editProduct")
    public String editProduct (Model model, @PathVariable("id") long id){
        ProductEntity productEntity =  productService.getProductById(id);
        model.addAttribute("product", productEntity);
        model.addAttribute("prodTitle", productEntity.getTitle());
        model.addAttribute("categories", productService.getAllCategories());
        return "products/editProduct";
    }

    @PatchMapping("/{id}")
    public String updateProduct(@ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult bindingResult,
                                @PathVariable("id") long id) {
        if (bindingResult.hasErrors()){
            return "products/editProduct";
        }
        productService.updateProduct(id, productDTO);
        return "redirect:/assortment/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") long id) {
        productService.deleteProduct(id);
        return "redirect:/assortment";
    }
}
