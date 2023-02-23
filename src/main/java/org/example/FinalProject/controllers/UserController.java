package org.example.FinalProject.controllers;

import org.example.FinalProject.enums.RoleOnSite;
import org.example.FinalProject.models.User;
import org.example.FinalProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
        User newUser = new User();
//        newUser.setUserDeleted(false);
//        newUser.setRole(RoleOnSite.CLIENT);
        model.addAttribute("user", newUser);
        return "entrance/registration";
    }


    @PostMapping("/registration")
    private String createUser(User user) {
        userService.createUser(user);
        return "redirect:/homepage";
    }
    @GetMapping("/login")
    public String showLoginPage(){
        return "entrance/login";
    }
//    @GetMapping("/user/create")
//    public String createUserForm(User user){
//        return "user-create";
//    }

}
