package org.example.FinalProject.controllers;

import jakarta.validation.Valid;
import org.example.FinalProject.dto.UserDTO;
import org.example.FinalProject.validator.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
    LoginValidator validator;

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") @Valid UserDTO userDTO,
                            BindingResult bindingResult) {
        validator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "entrance/login";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "entrance/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/confirmLogout")
    public String confirmLogout() {
        return "entrance/confirmLogout";
    }
}
