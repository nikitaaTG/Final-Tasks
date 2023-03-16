package org.example.FinalProject.controllers;

import jakarta.validation.Valid;
import org.example.FinalProject.dto.UserDTO;
import org.example.FinalProject.mappers.UserMapper;
import org.example.FinalProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
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




    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/all")
    public String showAllUsers(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, value = 2)
                    Pageable pageable,
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        // Settings of pagination:
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
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
    public String showUser(@PathVariable("id") long id, Model model) {
        UserDTO user = UserMapper.INSTANCE.userEntityToDTO(userService.getUserById(id));
        model.addAttribute("user", user);
        return "users/showUser";
    }

    @GetMapping("/{id}/editUser")
    public String editUser(Model model, @PathVariable("id") long id) {
        UserDTO user = UserMapper.INSTANCE.userEntityToDTO(userService.getUserById(id));
        model.addAttribute("user", user);
        return "users/editUser";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult,
                             @PathVariable("id") long id, Model model) {
        if (bindingResult.hasErrors()) {
            return "users/editUser";
        }
        userService.updateUser(id, userDTO);
        return "redirect:/user/{id}";
    }

    @GetMapping("/{id}/changePassword")
    public String editPassword(Model model, @PathVariable("id") long id) {
        UserDTO user = UserMapper.INSTANCE.userEntityToDTO(userService.getUserById(id));
        model.addAttribute("user", user);
        return "users/changePassword";
    }

    @PatchMapping("/{id}/changePassword")
    public String updatePassword(@ModelAttribute("oldPassword") @Valid String oldPassword,
                                 @ModelAttribute("password") @Valid String password,
                                 BindingResult bindingResult,
                                 @PathVariable("id") long id, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "users/editUser";
//        }
        //TODO: ADD VALIDATION BY PASSWORD PARAMS

        if (!userService.getUserById(id).getPassword().equals(oldPassword)) {
            model.addAttribute("user", userService.getUserById(id));
            return "users/changePassword";
        }
        userService.updatePassword(id, password);
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
