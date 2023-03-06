package org.example.FinalProject.controllers;

import jakarta.validation.Valid;
import org.example.FinalProject.dto.ProductDTO;
import org.example.FinalProject.dto.UserDTO;
import org.example.FinalProject.enums.RoleOnSite;
import org.example.FinalProject.mappers.CategoryMapper;
import org.example.FinalProject.mappers.ProductMapper;
import org.example.FinalProject.mappers.UserMapper;
import org.example.FinalProject.models.UserEntity;
import org.example.FinalProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/registration")
    public String showRegistrationPage(Model model) {
        UserEntity newUser = new UserEntity();
        newUser.setUserDeleted(false);
        newUser.setRole(RoleOnSite.CLIENT);
        model.addAttribute("user", newUser);
        return "entrance/registration";
    }


    @PostMapping("/registration")
    private String createUser(@ModelAttribute("user") @Valid UserDTO userDTO,
                              BindingResult bindingResult) {
        userDTO.setUserDeleted(false);
        userDTO.setRole(RoleOnSite.CLIENT);
        if (bindingResult.hasErrors()) {
            return "entrance/registration";
        }
        userService.createUser(userDTO);
        return "entrance/login";}
    @GetMapping("/login")
    public String showLoginPage(){
        return "entrance/login";
    }

    @GetMapping("/all")
    private String showAll(Model model, UserDTO userDTO, Pageable pageable) {
        model.addAttribute("users", userService.findAll(pageable));
        return "users/all";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        UserDTO user = UserMapper.INSTANCE.userEntityToDTO(userService.getUserById(id));
        model.addAttribute("user", user);
        return "users/showUser";
    }

    @PatchMapping("/{id}")
    public String updateProduct(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult,
                                @PathVariable("id") long id, Model model) {
        if (bindingResult.hasErrors()) {
            return "users/editUser";
        }
        userService.updateUser(id, userDTO);
        return "redirect:/user/{id}";
    }

}
