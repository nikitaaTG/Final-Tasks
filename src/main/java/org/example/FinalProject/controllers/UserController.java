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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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


    /**
     * ADMIN/MODERATOR SIDE:
     **/

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

        // Pagination of all users

        Page<UserDTO> users = userService.findAll(allProductsPage);
        model.addAttribute("users", users);

        // Counting the number of page
        List<Integer> pageNumbers = getPagesCount(users);
        model.addAttribute("pageNumbers", pageNumbers);

        return "users/all";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") long id,
                           Model model) {
        UserDTO userDTO = UserMapper.INSTANCE.userEntityToDTO(userService.getUserById(id));
        model.addAttribute("user", userDTO);


        return "users/showUser";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/{id}/editUser")
    public String editUser(Model model, @PathVariable("id") long id) {
        UserDTO userDTO = UserMapper.INSTANCE.userEntityToDTO(userService.getUserById(id));
        model.addAttribute("user", userDTO);

        return "users/editUser";
    }

    //??
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult,
                             @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "users/editUser";
        }
        userService.updateUser(id, userDTO);
        return "redirect:/user/{id}";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PatchMapping("/{id}/changePassword")
    public String updateUserPassword(@ModelAttribute("oldPassword") @Valid String oldPassword,
                                     @ModelAttribute("password") @Valid String password,
                                     BindingResult bindingResult,
                                     @PathVariable("id") long id, Model model) {

        if (!userService.getUserById(id).getPassword().equals(oldPassword)) {
            model.addAttribute("user", userService.getUserById(id));
            return "users/changePassword";
        }
        userService.updatePassword(id, password);
        return "redirect:/user/{id}";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/{id}/changePassword")
    public String editUserPassword(Model model,
                                   @PathVariable("id") long id,
                                   @AuthenticationPrincipal User user) {
        UserDTO userDTO = UserMapper.INSTANCE.userEntityToDTO(userService.getUserById(id));
        model.addAttribute("user", userDTO);

        return "users/changePassword";
    }

    /**
     * USER SIDE:
     **/

    @GetMapping("/self")
    public String showSelf(@AuthenticationPrincipal User user,
                           Model model) {
        UserDTO activeUser = userService.getUserByEmail(user.getUsername());
        model.addAttribute("user", activeUser);
        return "users/showUser";
    }

    @GetMapping("/self/editUser")
    public String editSelf(Model model,
                           @AuthenticationPrincipal User user) {
        UserDTO activeUser = userService.getUserByEmail(user.getUsername());
        model.addAttribute("user", activeUser);

        return "users/editUser";
    }

    //??
    @PatchMapping("/self")
    public String updateSelf(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult,
                             @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            return "users/editUser";
        }
        long id = userService.getUserByEmail(user.getUsername()).getId();
        userService.updateUser(id, userDTO);
        return "redirect:/user/self";
    }


    @GetMapping("/self/changePassword")
    public String editSelfPassword(Model model,
                                   @AuthenticationPrincipal User user) {
        UserDTO activeUser = userService.getUserByEmail(user.getUsername());
        model.addAttribute("user", activeUser);

        return "users/changePassword";
    }

    @PatchMapping("/self/changePassword")
    public String updateSelfPassword(
            @ModelAttribute("oldPassword") @Valid String oldPassword,
            @ModelAttribute("password") @Valid String password,
            @AuthenticationPrincipal User user,
            Model model) {

        long id = userService.getUserByEmail(user.getUsername()).getId();

        if (!userService.getUserById(id).getPassword().equals(oldPassword)) {
            model.addAttribute("user", userService.getUserById(id));
            return "users/changePassword";
        }
        userService.updatePassword(id, password);
        return "redirect:/user/self";
    }

    public List<Integer> getPagesCount(Page<UserDTO> userPages) {
        int totalPages = userPages.getTotalPages();
        List<Integer> result = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
        return result;
    }

}
