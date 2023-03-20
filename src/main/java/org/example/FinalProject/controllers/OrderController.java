package org.example.FinalProject.controllers;

import jakarta.validation.Valid;
import org.example.FinalProject.dto.Cart;
import org.example.FinalProject.dto.OrderDTO;
import org.example.FinalProject.mappers.OrderMapper;
import org.example.FinalProject.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
   private OrderService orderService;




    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/all")
    public String showAllOrders(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, value = 2)
                    Pageable pageable,
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        // Settings of pagination:
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Pageable allProductsPage = PageRequest.of(currentPage - 1, pageSize);

        // Pagination of all products

        Page<OrderDTO> orders = orderService.findAll(allProductsPage);
        model.addAttribute("orders", orders);

        // Counting the number of page
        List<Integer> pageNumbers = getPagesCount(orders);
        model.addAttribute("pageNumbers", pageNumbers);

        return "/all";
}

    @GetMapping("/{id}")
    public String showOrder(@PathVariable("id") long id, Model model) {
        OrderDTO order = OrderMapper.INSTANCE.orderEntityToDTO(orderService.getOrderById(id));
        model.addAttribute("order", order);
        return "orders/showOrder";
    }

    @GetMapping("/{id}/editOrder")
    public String editOrder(Model model, @PathVariable("id") long id) {
        OrderDTO order = OrderMapper.INSTANCE.orderEntityToDTO(orderService.getOrderById(id));
        model.addAttribute("order", order);
        return "orders/editOrder";
    }

    @GetMapping("/new")
    public String showNewOrderPage(){
        return "orders/newOrderPage";
    }

    @PostMapping
    private String createNewOrder(@ModelAttribute("order") @Valid OrderDTO orderDTO,
                                  @ModelAttribute("cart") Cart cart) {
        orderService.createNewOrder(orderDTO, cart);
        return "/homepage/homepage";
    }

//    @PatchMapping("/{id}")
//    public String updateOrder(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult,
//                             @PathVariable("id") long id, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "users/editUser";
//        }
//        userService.updateUser(id, userDTO);
//        return "redirect:/order/{id}";
//    }

//    @GetMapping("/{id}/changePassword")
//    public String editPassword(Model model, @PathVariable("id") long id) {
//        UserDTO user = UserMapper.INSTANCE.userEntityToDTO(userService.getUserById(id));
//        model.addAttribute("user", user);
//        return "users/changePassword";
//    }
//
//    @PatchMapping("/{id}/changePassword")
//    public String updatePassword(@ModelAttribute("oldPassword") @Valid String oldPassword,
//                                 @ModelAttribute("password") @Valid String password,
//                                 BindingResult bindingResult,
//                                 @PathVariable("id") long id, Model model) {
////        if (bindingResult.hasErrors()) {
////            return "users/editUser";
////        }
//        //TODO: ADD VALIDATION BY PASSWORD PARAMS
//
//        if (!userService.getUserById(id).getPassword().equals(oldPassword)) {
//            model.addAttribute("user", userService.getUserById(id));
//            return "users/changePassword";
//        }
//        userService.updatePassword(id, password);
//        return "redirect:/user/{id}";
//    }

    public List<Integer> getPagesCount(Page<OrderDTO> orderPages) {
        int totalPages = orderPages.getTotalPages();
        List<Integer> result = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
        return result;
    }

}
