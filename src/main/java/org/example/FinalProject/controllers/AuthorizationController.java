package org.example.FinalProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorizationController {
    @GetMapping("/login")
    public String showLoginPage(){
        return "entrance/login";
    }

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "entrance/registration";
    }

}
