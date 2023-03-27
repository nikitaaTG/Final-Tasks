package org.example.FinalProject.controllers;

import jakarta.validation.Valid;
import org.example.FinalProject.dto.*;
import org.example.FinalProject.enums.DeliveryMethod;
import org.example.FinalProject.enums.OrderStatus;
import org.example.FinalProject.enums.PaymentMethod;
import org.example.FinalProject.enums.PaymentStatus;
import org.example.FinalProject.mappers.AddressMapper;
import org.example.FinalProject.mappers.OrderMapper;
import org.example.FinalProject.mappers.UserMapper;
import org.example.FinalProject.services.OrderService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/order")
@SessionAttributes("cart")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    double totalPrice = 0;

    /**
     * ADMIN/MODERATOR SIDE:
     **/

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public String showAllOrders(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, value = 2)
                    Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        // Settings of pagination:
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(18);
        Pageable allProductsPage = PageRequest.of(currentPage - 1, pageSize);

        // Pagination of all products

        Page<OrderDTO> orders = orderService.findAll(allProductsPage);
        model.addAttribute("orders", orders);

        // Counting the number of page
        List<Integer> pageNumbers = getPagesCount(orders);
        model.addAttribute("pageNumbers", pageNumbers);

        return "orders/allOrders";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public String showOrder(@PathVariable("id") long id, Model model) {
        OrderDTO order = OrderMapper.INSTANCE.orderEntityToDTO(orderService.getOrderById(id));
        AddressDTO address = AddressMapper.INSTANCE.addressEntityToDTO(order.getAddress());
        UserDTO user = UserMapper.INSTANCE.userEntityToDTO(order.getUser());
        model.addAttribute("order", order);
        model.addAttribute("address", address);
        model.addAttribute("user", user);
        model.addAttribute("products", order.getProductsInOrder());
        return "orders/showOrder";
    }


    @GetMapping("/{id}/editOrder")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public String editOrder(Model model, @PathVariable("id") long id) {
        OrderDTO order = OrderMapper.INSTANCE.orderEntityToDTO(orderService.getOrderById(id));
        model.addAttribute("order", order);
        model.addAttribute("paymentStatus", PaymentStatus.values());
        model.addAttribute("orderStatus", OrderStatus.values());
        return "orders/editOrder";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public String updateOrder(@ModelAttribute("user") @Valid OrderDTO changedOrder,
                              @PathVariable("id") long id, Model model) {

        orderService.updateOrder(id, changedOrder);
        return "redirect:/order/{id}";
    }

    /**
     * USER SIDE:
     **/

    @GetMapping("/self/all")
    public String showAllSelfOrders(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, value = 2)
                    Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @AuthenticationPrincipal User user) {
        // Settings of pagination:
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(18);
        Pageable allProductsPage = PageRequest.of(currentPage - 1, pageSize);

        // Pagination of all products

        UserDTO activeUser = userService.getUserByEmail(user.getUsername());
        long userId = activeUser.getId();
        Page<OrderDTO> userOrders = orderService.findByUserId(userId, allProductsPage);
        model.addAttribute("activeUser", activeUser);
        model.addAttribute("orders", userOrders);

        // Counting the number of page
        List<Integer> pageNumbers = getPagesCount(userOrders);
        model.addAttribute("pageNumbers", pageNumbers);

        return "orders/allUserOrders";
    }

    @GetMapping("/self/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showSelfOrder(@PathVariable("id") long id, Model model) {
        OrderDTO order = OrderMapper.INSTANCE.orderEntityToDTO(orderService.getOrderById(id));
        AddressDTO address = AddressMapper.INSTANCE.addressEntityToDTO(order.getAddress());
        UserDTO user = UserMapper.INSTANCE.userEntityToDTO(order.getUser());
        model.addAttribute("order", order);
        model.addAttribute("address", address);
        model.addAttribute("user", user);
        model.addAttribute("products", order.getProductsInOrder());
        return "orders/showOrder";
    }

    @GetMapping("/newOrder")
    @PreAuthorize("isAuthenticated()")
    public String showOrderConfirmPage(Model model,
                                       RedirectAttributes attributes,
                                       @ModelAttribute("cart") Cart cart,
                                       @AuthenticationPrincipal User user) {
        UserDTO activeUser = userService.getUserByEmail(user.getUsername());
        model.addAttribute("activeUser", activeUser);
        model.addAttribute("deliveryMethod", DeliveryMethod.values());
        model.addAttribute("paymentMethod", PaymentMethod.values());
        Set<AddressDTO> addressDTOSet = AddressMapper.INSTANCE.setDTO(activeUser.getAddressEntities());
        model.addAttribute("addresses", addressDTOSet);
        attributes.addFlashAttribute("cart", cart);
        for (ProductDTO prod : cart) {
            totalPrice += prod.getPrice();
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("newOrder", new OrderDTO());
        return "orders/confirmNewOrderPage";
    }

    @PostMapping("/newOrder")
    @PreAuthorize("isAuthenticated()")
    public String createNewOrder(@ModelAttribute("order") @Valid OrderDTO orderDTO,
                                 @ModelAttribute("cart") Cart cart,
                                 @AuthenticationPrincipal User user) {
        orderDTO.setPaymentStatus(PaymentStatus.PENDING);
        orderDTO.setOrderStatus(OrderStatus.PENDING_PAYMENT);
        orderDTO.setUser(UserMapper.INSTANCE.userDTOToEntity(userService.getUserByEmail(user.getUsername())));
        orderService.createNewOrder(orderDTO, cart);
        cart.removeAll(cart);
        totalPrice = 0;
        return "/homepage/homepage";
    }


    public List<Integer> getPagesCount(Page<OrderDTO> orderPages) {
        int totalPages = orderPages.getTotalPages();
        List<Integer> result = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
        return result;
    }

}
