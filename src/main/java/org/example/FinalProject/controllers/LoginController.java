package org.example.FinalProject.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @PostMapping("/login")
    public String loginUser() {
        return "/";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "entrance/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/confirmLogout")
    public String confirmLogout() {
        return "entrance/confirmLogout";
    }
}
