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

/**
 * Controller for all users queries
 */

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final int currentPageNormal = 1;
    private final int pageSizeNormal = 20;

    private static final String USER = "user";
    private static final String USERS = "users";
    private static final String PAGE_NUMBERS = "pageNumbers";


    /**
     * ADMIN/MODERATOR SIDE:
     **/

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/all")
    public String showAllUsers(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, value = 2)
                    Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        // Settings of pagination:
        int currentPage = page.orElse(currentPageNormal);
        int pageSize = size.orElse(pageSizeNormal);
        Pageable allProductsPage = PageRequest.of(currentPage - 1, pageSize);

        // Pagination of all users

        Page<UserDTO> users = userService.findAll(allProductsPage);
        model.addAttribute(USERS, users);

        // Counting the number of page
        List<Integer> pageNumbers = getPagesCount(users);
        model.addAttribute(PAGE_NUMBERS, pageNumbers);

        return "users/all";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") long id, Model model) {

        UserDTO userDTO = UserMapper.INSTANCE.userEntityToDTO(userService.getUserById(id));
        model.addAttribute(USER, userDTO);

        return "users/showUser";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/{id}/editUser")
    public String editUser(Model model, @PathVariable("id") long id) {

        UserDTO userDTO = UserMapper.INSTANCE.userEntityToDTO(userService.getUserById(id));
        model.addAttribute(USER, userDTO);

        return "users/editUser";
    }

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
    @GetMapping("/{id}/changePassword")
    public String editUserPassword(Model model,
                                   @PathVariable("id") long id,
                                   @AuthenticationPrincipal User user) {

        UserDTO userDTO = UserMapper.INSTANCE.userEntityToDTO(userService.getUserById(id));
        model.addAttribute(USER, userDTO);

        return "users/changePassword";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PatchMapping("/{id}/changePassword")
    public String updateUserPassword(@ModelAttribute("oldPassword") @Valid String oldPassword,
                                     @ModelAttribute("password") @Valid String password,
                                     BindingResult bindingResult,
                                     @PathVariable("id") long id, Model model) {

        //validation of old password (if user enters the wrong password, the new password will not be written)
        if (!userService.getUserById(id).getPassword().equals(oldPassword)) {
            model.addAttribute(USER, userService.getUserById(id));
            return "users/changePassword";
        }
        userService.updatePassword(id, password);
        return "redirect:/user/{id}";
    }

    /**
     * USER SIDE:
     **/

    @GetMapping("/self")
    public String showSelf(@AuthenticationPrincipal User user, Model model) {

        UserDTO activeUser = userService.getUserByEmail(user.getUsername());
        model.addAttribute(USER, activeUser);

        return "users/showUser";
    }

    @GetMapping("/self/editUser")
    public String editSelf(Model model, @AuthenticationPrincipal User user) {

        UserDTO activeUser = userService.getUserByEmail(user.getUsername());
        model.addAttribute(USER, activeUser);

        return "users/editUser";
    }

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
    public String editSelfPassword(Model model, @AuthenticationPrincipal User user) {

        UserDTO activeUser = userService.getUserByEmail(user.getUsername());
        model.addAttribute(USER, activeUser);

        return "users/changePassword";
    }

    @PatchMapping("/self/changePassword")
    public String updateSelfPassword(
            @ModelAttribute("oldPassword") @Valid String oldPassword,
            @ModelAttribute("password") @Valid String password,
            @AuthenticationPrincipal User user,
            Model model) {

        long id = userService.getUserByEmail(user.getUsername()).getId();

        //validation of old password (if user enters the wrong password, the new password will not be written)
        if (!userService.getUserById(id).getPassword().equals(oldPassword)) {
            model.addAttribute(USER, userService.getUserById(id));
            return "users/changePassword";
        }
        userService.updatePassword(id, password);
        return "redirect:/user/self";
    }

    /**
     * Method to calculate the total number of pages and add it to the list for further enumeration one by one
     *
     * @param userPages
     */
    public List<Integer> getPagesCount(Page<UserDTO> userPages) {
        int totalPages = userPages.getTotalPages();
        List<Integer> result = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
        return result;
    }

}
