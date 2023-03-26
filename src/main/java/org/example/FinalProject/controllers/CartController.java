package org.example.FinalProject.controllers;

import org.example.FinalProject.dto.Cart;
import org.example.FinalProject.dto.ProductDTO;
import org.example.FinalProject.mappers.ProductMapper;
import org.example.FinalProject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/assortment")
@SessionAttributes("cart")
public class CartController {
    @Autowired
    ProductService productService; // FIXME: не делай просто пекедж прайвт без необходимости. пусть будет прайвет

    // FIXME: о это ужас! срочно править. У тебя контроллет теперь имеет стейт, который шарится между разными сессиями. Т.е. оба клиента будут видеть одни и те же данные
    static double totalPrice = 0;

    @GetMapping("/cart")
    public String showCart(
            Model model,
            @ModelAttribute("cart") Cart cart) {
        model.addAttribute("totalPrice", totalPrice);
        return "cart/cart";
    }

    @PostMapping("/{id}/addToCart")
    public String addToCart(
            @ModelAttribute("cart") Cart cart,
            @PathVariable("id") long id,
            RedirectAttributes attributes,
            Model model) {
        ProductDTO neededProduct = ProductMapper.INSTANCE.productEntityToDTO(productService.getProductById(id));
        cart.add(neededProduct);
        totalPrice += neededProduct.getPrice();
        model.addAttribute("totalPrice", totalPrice);
        attributes.addFlashAttribute("cart", cart);
        return "cart/cart";
    }

    @GetMapping("/cart/deleteFromCart/{index}")
    public String deleteFromCart(
            @ModelAttribute("cart") Cart cart,
            @PathVariable("index") int index,
            RedirectAttributes attributes,
            Model model) {
        totalPrice -= cart.get(index).getPrice();
        cart.remove(index);
        attributes.addFlashAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        return "redirect:/assortment/cart";
    }

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }

}
