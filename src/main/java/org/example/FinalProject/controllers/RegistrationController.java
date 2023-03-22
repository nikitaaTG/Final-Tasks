package org.example.FinalProject.controllers;

import jakarta.validation.Valid;
import org.example.FinalProject.dto.UserDTO;
import org.example.FinalProject.enums.RoleOnSite;
import org.example.FinalProject.services.UserService;
import org.example.FinalProject.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserValidator validator;

    @GetMapping
    @PreAuthorize("!isAuthenticated()")
    public String showRegistrationPage(Model model) {
        UserDTO newUser = new UserDTO();
        newUser.setUserDeleted(false);
        newUser.setRole(RoleOnSite.CLIENT);
        model.addAttribute("user", newUser);
        return "entrance/registration";
    }


    @PostMapping
    private String createNewUser(@ModelAttribute("user") @Valid UserDTO userDTO,
                                 BindingResult bindingResult) {
        userDTO.setUserDeleted(false);
        userDTO.setRole(RoleOnSite.CLIENT);
        validator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "entrance/registration";
        }
        userService.createUser(userDTO);
        return "entrance/login";
    }
}
