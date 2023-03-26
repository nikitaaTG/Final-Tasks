package org.example.FinalProject.controllers;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class AddressController {

    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;

    /**
     * ADMIN/MODERATOR SIDE:
     **/

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/{userId}/changeAddress")
    public String changeUserAddress(Model model,
            // FIXME: не забывай форматировать код, зачем перенос
            @PathVariable("userId") long userId) {
        model.addAttribute("userId", userId);
        model.addAttribute("addresses", AddressMapper.INSTANCE.listDTO(addressService.findByUserID(userId)));
        return "address/editAddress";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/{userId}/addAddress")
    public String newUserAddress(Model model,
                                 @PathVariable("userId") long userId) {
        model.addAttribute("userId", userId);
        model.addAttribute("address", new AddressDTO());
        return "address/addAddress";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/{userId}/addAddress")
    public String addNewUserAddress(@ModelAttribute("address") @Valid AddressDTO address,
                                    @PathVariable("userId") long userId) {

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
        model.addAttribute("userId", userId);
        model.addAttribute("id", id);
        return "address/correctAddress";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PatchMapping("/{userId}/changeAddress/{id}")
    public String updateUserAddress(@ModelAttribute("product") @Valid AddressDTO addressDTO,
                                    @PathVariable("userId") long userId,
                                    @PathVariable("id") long id) {
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
        model.addAttribute("userId", userId);
        model.addAttribute("addresses", AddressMapper.INSTANCE.listDTO(addressService.findByUserID(userId)));
        return "address/editAddress";
    }

    @GetMapping("/self/addAddress")
    public String newSelfAddress(Model model,
                                 @AuthenticationPrincipal User user) {
        long userId = userService.getUserByEmail(user.getUsername()).getId();
        model.addAttribute("userId", userId);
        model.addAttribute("address", new AddressDTO());
        return "address/addAddress";
    }

    @PostMapping("/self/addAddress")
    public String addNewSelfAddress(@ModelAttribute("address") @Valid AddressDTO address,
                                    @AuthenticationPrincipal User user) {
        long userId = userService.getUserByEmail(user.getUsername()).getId();

        address.setUserId(userId);

        addressService.saveAddress(address, userId);
        return "redirect:/user/self/changeAddress";
    }

    @GetMapping("/self/changeAddress/{id}")
    public String editSelfAddress(Model model,
                                  @AuthenticationPrincipal User user,
                                  @PathVariable("id") long id) {
        AddressDTO address = AddressMapper.INSTANCE.addressEntityToDTO(addressService.getAddressById(id));
        long userId = userService.getUserByEmail(user.getUsername()).getId();
        model.addAttribute("address", address);
        model.addAttribute("userId", userId);
        model.addAttribute("id", id);
        return "address/correctAddress";
    }

    @PatchMapping("/self/changeAddress/{id}")
    public String updateSelfAddress(@ModelAttribute("product") @Valid AddressDTO addressDTO,
                                    @PathVariable("id") long id) {
        addressService.updateAddress(id, addressDTO);
        return "redirect:/user/self/changeAddress";
    }
}
