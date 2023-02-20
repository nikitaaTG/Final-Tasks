package org.example.FinalProject.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {
    @GetMapping("/")
    public String showHomepage(){
        return "homepage/homepage";
    }

    @GetMapping("/about")
    public String showSiteInfo(){ return "homepage/about";}

    @GetMapping("/products")
    public String showProductsMenu(){
        return "products/index";
    }
}
