package org.example.FinalProject.controllers;

import jakarta.validation.Valid;
import org.example.FinalProject.dto.AddressDTO;
import org.example.FinalProject.mappers.AddressMapper;
import org.example.FinalProject.services.AddressService;
import org.example.FinalProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class AddressController {

    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;

    @GetMapping("/{userId}/changeAddress")
    public String changeAddress(Model model, @PathVariable("userId") long userId) {
        model.addAttribute("userId", userId);
        model.addAttribute("addresses", AddressMapper.INSTANCE.listDTO(addressService.findByUserID(userId)));
        return "address/editAddress";
    }

    @RequestMapping(value = "/{userId}/addAddress", method = RequestMethod.GET)
    public String newProduct(Model model, @PathVariable("userId") long userId) {
        model.addAttribute("userId", userId);
        model.addAttribute("address", new AddressDTO());
        return "address/addAddress";
    }

    @RequestMapping(value = "/{userId}/addAddress", method = RequestMethod.POST)
    public String addNewAddress(@ModelAttribute("address") @Valid AddressDTO address,
                                BindingResult bindingResult,
                                @PathVariable("userId") long userId,
                                Model model) {
//        if (bindingResult.hasErrors()) {
//            return "products/addProduct";
//        }
        address.setUserId(userId);

        addressService.saveAddress(address, userId);
        return "redirect:/user/{userId}/changeAddress";
    }

    @GetMapping("/{userId}/changeAddress/{id}")
    public String editProduct(Model model, @PathVariable("userId") long userId,
                              @PathVariable("id") long id) {
        AddressDTO address = AddressMapper.INSTANCE.addressEntityToDTO(addressService.getAddressById(id));
        model.addAttribute("address", address);
        model.addAttribute("userId", userId);
        model.addAttribute("id", id);
        return "address/correctAddress";
    }

    @PatchMapping("/{userId}/changeAddress/{id}")
    public String updateProduct(@ModelAttribute("product") @Valid AddressDTO addressDTO, BindingResult bindingResult,
                                @PathVariable("userId") long userId,
                                @PathVariable("id") long id, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "products/editProduct";
//        }
        addressService.updateAddress(id, addressDTO);
        return "redirect:/user/{userId}/changeAddress";
    }

//    // HOW TO LIMIT ACCESS???
//    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
//    //??????????????????????? - CORRECT
//    @DeleteMapping("/{id}")
//    public String deleteProduct(@PathVariable("id") long id) {
//        productService.deleteProduct(id);
//        return "redirect:/assortment";
//    }


//    // Counting the number of page method
//    public List<Integer> getPagesCount(Page<ProductDTO> productPages) {
//        int totalPages = productPages.getTotalPages();
//        List<Integer> result = IntStream.rangeClosed(1, totalPages)
//                .boxed()
//                .collect(Collectors.toList());
//        return result;
//    }
}
