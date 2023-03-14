package org.example.FinalProject.controllers;

import jakarta.validation.Valid;
import org.example.FinalProject.dto.AddressDTO;
import org.example.FinalProject.dto.UserDTO;
import org.example.FinalProject.mappers.AddressMapper;
import org.example.FinalProject.mappers.UserMapper;
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

    //
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public String index(
//            @PageableDefault (sort = "id", direction = Sort.Direction.ASC, value = 2)
//            Pageable pageable,
//            Model model,
//            @RequestParam ("page") Optional<Integer> page,
//            @RequestParam ("size") Optional<Integer> size,
//            @RequestParam("categoryId") Optional<Long> categoryId) {
//        // Settings of pagination:
//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(10);
//        Pageable allProductsPage = PageRequest.of(currentPage - 1, pageSize);
//
//        //  Add Model attribute for view list of category in filter
//        model.addAttribute("categories", CategoryMapper.INSTANCE.listDTO(productService.getAllCategories()));
//        return categoryId.map(cat -> {
//            // Pagination of all products in category filter
//
//            Page<ProductDTO> productInCategory = productService.listProductsByCategory(cat, allProductsPage);
//            model.addAttribute("productPage", productInCategory);
//            List<Integer> pageNumbers = getPagesCount(productInCategory);
//            model.addAttribute("pageNumbers", pageNumbers);
//            model.addAttribute("categoryName", CategoryMapper.INSTANCE.categoryEntityToDTO(productService.getCategoryById(cat)).getName());
//            return "products/indexCategory";
//        }).orElseGet(() -> {
//            // Pagination of all products
//            Page<ProductDTO> productPage = productService.listProducts(allProductsPage);
//            model.addAttribute("productPage", productPage);
//
//            // Counting the number of page
//            List<Integer> pageNumbers = getPagesCount(productPage);
//            model.addAttribute("pageNumbers", pageNumbers);
//
//            return "products/index";
//        });
//    }
//
//
//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") long id, Model model) {
//        ProductDTO product = ProductMapper.INSTANCE.productEntityToDTO(productService.getProductById(id));
//        model.addAttribute("product", product);
//        model.addAttribute("prodTitle", product.getTitle());
//        return "products/show";
//    }
//
    @GetMapping("/{id}/changeAddress")
    public String editAddress(Model model, @PathVariable("id") long id) {
        UserDTO user = UserMapper.INSTANCE.userEntityToDTO(userService.findByID(id));
        model.addAttribute("user", user);
        model.addAttribute("userId", id);
        model.addAttribute("addresses", AddressMapper.INSTANCE.listDTO(addressService.findByUserID(id)));
        return "address/editAddress";
    }
//    @GetMapping("/{id}/addAddress")
//    public String addAddress(Model model, @PathVariable("id") long id) {
//        UserDTO user = UserMapper.INSTANCE.userEntityToDTO(userService.findByID(id));
//        model.addAttribute("user", user);
//        model.addAttribute("addresses", AddressMapper.INSTANCE.listDTO(addressService.findByUserID(id)));
//        return "address/addAddress";
//    }

    //    @GetMapping("/{id}/addAddresses")
    @RequestMapping(value = "/{id}/addAddress", method = RequestMethod.GET)
    public String newProduct(Model model, @PathVariable("id") long id) {
        model.addAttribute("userId", id);
        model.addAttribute("address", new AddressDTO());
        return "address/addAddress";
    }

    //    @PostMapping("/{id}/addAddress")
    @RequestMapping(value = "/{id}/addAddress", method = RequestMethod.POST)
    public String addNewAddress(@ModelAttribute("address") @Valid AddressDTO address,
                                BindingResult bindingResult,
                                @PathVariable("id") long id,
                                Model model) {
//        if (bindingResult.hasErrors()) {
//            return "products/addProduct";
//        }
        address.setUserId(id);
        addressService.saveAddress(address, id);
        return "redirect:/user/{id}/changeAddress";
    }

//    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
//    @PatchMapping("/{id}")
//    public String updateProduct(@ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult bindingResult,
//                                @PathVariable("id") long id, Model model) {
//        model.addAttribute("categories", CategoryMapper.INSTANCE.listDTO(productService.getAllCategories()));
//        if (bindingResult.hasErrors()) {
//            return "products/editProduct";
//        }
//        productService.updateProduct(id, productDTO);
//        return "redirect:/assortment/{id}";
//    }

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
