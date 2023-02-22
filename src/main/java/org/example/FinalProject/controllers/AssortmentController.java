package org.example.FinalProject.controllers;

import jakarta.validation.Valid;
import org.example.FinalProject.DAO.ProductDAO;
import org.example.FinalProject.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assortment")
public class AssortmentController {

    @Autowired
    private ProductDAO productDAO;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("products", productDAO.index());
    return "products/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        Product product = productDAO.show(id);
        model.addAttribute("product", product);
        model.addAttribute("prodTitle", product.getTitle());
    return "products/show";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "products/addProduct";
    }

    @PostMapping()
    public String create(@ModelAttribute("product") @Valid Product product,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "products/addProduct"; }
        productDAO.save(product);
        return "redirect:/assortment";
    }

    @GetMapping("/{id}/editProduct")
    public String editProduct (Model model, @PathVariable("id") long id){
        Product product =  productDAO.show(id);
        model.addAttribute("product", product);
        model.addAttribute("prodTitle", product.getTitle());
        return "products/editProduct";
    }

    @PatchMapping("/{id}")
    public String updateProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
                                @PathVariable("id") long id) {
        if (bindingResult.hasErrors()){
            return "products/editProduct";
        }
        productDAO.update(id, product);
        return "redirect:/assortment/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") long id) {
        productDAO.delete(id);
        return "redirect:/assortment";
    }
}
