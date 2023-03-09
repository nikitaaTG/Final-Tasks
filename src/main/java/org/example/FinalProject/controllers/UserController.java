package org.example.FinalProject.controllers;

import jakarta.validation.Valid;
import org.example.FinalProject.dto.UserDTO;
import org.example.FinalProject.enums.RoleOnSite;
import org.example.FinalProject.mappers.UserMapper;
import org.example.FinalProject.models.UserEntity;
import org.example.FinalProject.services.UserService;
import org.example.FinalProject.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    UserValidator validator;

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
        validator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "entrance/registration";
        }
        userService.createUser(userDTO);
        return "entrance/login";
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String showAll(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, value = 2)
                    Pageable pageable,
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        // Settings of pagination:
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Pageable allProductsPage = PageRequest.of(currentPage - 1, pageSize);

        // Pagination of all products

        Page<UserDTO> users = userService.findAll(allProductsPage);
        model.addAttribute("users", users);

        // Counting the number of page
        List<Integer> pageNumbers = getPagesCount(users);
        model.addAttribute("pageNumbers", pageNumbers);

        return "users/all";
}

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        UserDTO user = UserMapper.INSTANCE.userEntityToDTO(userService.getUserById(id));
        model.addAttribute("user", user);
        return "users/showUser";
    }

    @GetMapping("/{id}/editUser")
    public String editProduct(Model model, @PathVariable("id") long id) {
        UserDTO user = UserMapper.INSTANCE.userEntityToDTO(userService.getUserById(id));
        model.addAttribute("user", user);
        return "users/editUser";
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

    public List<Integer> getPagesCount(Page<UserDTO> userPages) {
        int totalPages = userPages.getTotalPages();
        List<Integer> result = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
        return result;
    }

}
