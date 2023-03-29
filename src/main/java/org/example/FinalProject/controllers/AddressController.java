package org.example.FinalProject.controllers;

import jakarta.validation.Valid;
import org.example.FinalProject.dto.AddressDTO;
import org.example.FinalProject.mappers.AddressMapper;
import org.example.FinalProject.services.AddressService;
import org.example.FinalProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for all address queries
 */

@Controller
@RequestMapping("/user")
public class AddressController {

    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;

    private static final String USER_ID = "userId";
    private static final String ADDRESS = "address";
    private static final String ADDRESSES = "addresses";
    private static final String ADDRESS_ID = "id";

    /**
     * ADMIN/MODERATOR SIDE:
     **/

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/{userId}/changeAddress")
    public String changeUserAddress(Model model,
                                    @PathVariable("userId") long userId) {
        model.addAttribute(USER_ID, userId);
        model.addAttribute(ADDRESSES, AddressMapper.INSTANCE.listDTO(addressService.findByUserID(userId)));
        return "address/editAddress";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/{userId}/addAddress")
    public String newUserAddress(Model model,
                                 @PathVariable("userId") long userId) {
        model.addAttribute(USER_ID, userId);
        model.addAttribute(ADDRESS, new AddressDTO());
        return "address/addAddress";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/{userId}/addAddress")
    public String addNewUserAddress(@ModelAttribute("address") @Valid AddressDTO address, BindingResult bindingResult,
                                    @PathVariable("userId") long userId) {

        if (bindingResult.hasErrors()) {
            return "address/addAddress";
        }
        address.setUserId(userId);
        addressService.saveAddress(address, userId);
        return "redirect:/user/{userId}/changeAddress";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/{userId}/changeAddress/{id}")
    public String editUserAddress(Model model, @PathVariable("userId") long userId,
                                  @PathVariable("id") long id) {
        AddressDTO address = AddressMapper.INSTANCE.addressEntityToDTO(addressService.getAddressById(id));
        model.addAttribute("address", address);
        model.addAttribute(USER_ID, userId);
        model.addAttribute(ADDRESS_ID, id);
        return "address/correctAddress";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PatchMapping("/{userId}/changeAddress/{id}")
    public String updateUserAddress(@ModelAttribute("product") @Valid AddressDTO addressDTO, BindingResult bindingResult,
                                    @PathVariable("userId") long userId,
                                    @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "address/correctAddress";
        }
        addressService.updateAddress(id, addressDTO);
        return "redirect:/user/{userId}/changeAddress";
    }

    /**
     * USER SIDE:
     **/

    @GetMapping("/self/changeAddress")
    public String changeSelfAddress(Model model,
                                    @AuthenticationPrincipal User user) {
        long userId = userService.getUserByEmail(user.getUsername()).getId();
        model.addAttribute(USER_ID, userId);
        model.addAttribute(ADDRESSES, AddressMapper.INSTANCE.listDTO(addressService.findByUserID(userId)));
        return "address/editAddress";
    }

    @GetMapping("/self/addAddress")
    public String newSelfAddress(Model model,
                                 @AuthenticationPrincipal User user) {
        long userId = userService.getUserByEmail(user.getUsername()).getId();
        model.addAttribute(USER_ID, userId);
        model.addAttribute(ADDRESS, new AddressDTO());
        return "address/addAddress";
    }

    @PostMapping("/self/addAddress")
    public String addNewSelfAddress(@ModelAttribute("address") @Valid AddressDTO address, BindingResult bindingResult,
                                    @AuthenticationPrincipal User user) {
        long userId = userService.getUserByEmail(user.getUsername()).getId();
        if (bindingResult.hasErrors()) {
            return "address/addAddress";
        }
        address.setUserId(userId);
        addressService.saveAddress(address, userId);
        return "redirect:/user/self/changeAddress";
    }

    @GetMapping("/self/changeAddress/{id}")
    public String editSelfAddress(Model model,
                                  @AuthenticationPrincipal User user,
                                  @PathVariable("id") long addressId) {
        AddressDTO address = AddressMapper.INSTANCE.addressEntityToDTO(addressService.getAddressById(addressId));
        long userId = userService.getUserByEmail(user.getUsername()).getId();
        model.addAttribute(ADDRESS, address);
        model.addAttribute(USER_ID, userId);
        model.addAttribute(ADDRESS_ID, addressId);
        return "address/correctAddress";
    }

    @PatchMapping("/self/changeAddress/{id}")
    public String updateSelfAddress(@ModelAttribute("product") @Valid AddressDTO addressDTO, BindingResult bindingResult,
                                    @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "address/correctAddress";
        }
        addressService.updateAddress(id, addressDTO);
        return "redirect:/user/self/changeAddress";
    }
}
