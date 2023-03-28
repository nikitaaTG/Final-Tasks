package org.example.FinalProject.controllers;

import org.example.FinalProject.dto.UserDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private static final String USER = "user";

    @PostMapping("/login")
    public String loginUser() {
        return "redirect:/";
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute(USER, new UserDTO());
        return "entrance/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/confirmLogout")
    public String confirmLogout() {
        return "entrance/confirmLogout";
    }
}
