package org.example.FinalProject.controllers;

import jakarta.validation.Valid;
import org.example.FinalProject.dto.*;
import org.example.FinalProject.enums.DeliveryMethod;
import org.example.FinalProject.enums.OrderStatus;
import org.example.FinalProject.enums.PaymentMethod;
import org.example.FinalProject.enums.PaymentStatus;
import org.example.FinalProject.mappers.AddressMapper;
import org.example.FinalProject.mappers.OrderMapper;
import org.example.FinalProject.services.OrderService;
import org.example.FinalProject.services.ProductService;
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
    @Autowired
    private ProductService productService;

    private double totalPrice = 0;

    private final int currentPageNormal = 1;
    private final int pageSizeNormal = 18;

    private static final String ORDERS = "orders";
    private static final String PAGE_NUMBERS = "pageNumbers";
    private static final String ORDER = "order";
    private static final String ADDRESS = "address";
    private static final String ADDRESSES = "addresses";
    private static final String USER = "user";
    private static final String PRODUCTS = "products";
    private static final String PAYMENT_STATUS = "paymentStatus";
    private static final String ORDER_STATUS = "orderStatus";
    private static final String DELIVERY_METHOD = "deliveryMethod";
    private static final String PAYMENT_METHOD = "paymentMethod";
    private static final String ACTIVE_USER = "activeUser";
    private static final String CART = "cart";
    private static final String NEW_ORDER = "newOrder";
    private static final String TOTAL_PRICE = "totalPrice";

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
        int currentPage = page.orElse(currentPageNormal);
        int pageSize = size.orElse(pageSizeNormal);
        Pageable allProductsPage = PageRequest.of(currentPage - 1, pageSize);

        // Pagination of all products

        Page<OrderDTO> orders = orderService.findAll(allProductsPage);
        model.addAttribute(ORDERS, orders);

        // Counting the number of page
        List<Integer> pageNumbers = getPagesCount(orders);
        model.addAttribute(PAGE_NUMBERS, pageNumbers);

        return "orders/allOrders";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public String showOrder(@PathVariable("id") long id, Model model) {
        OrderDTO order = OrderMapper.INSTANCE.orderEntityToDTO(orderService.getOrderById(id));
        AddressDTO address = AddressMapper.INSTANCE.addressEntityToDTO(order.getAddress());
        UserDTO user = (order.getUser());
        model.addAttribute(ORDER, order);
        model.addAttribute(ADDRESS, address);
        model.addAttribute(USER, user);
        model.addAttribute(PRODUCTS, order.getProductsInOrder());
        return "orders/showOrder";
    }


    @GetMapping("/{id}/editOrder")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public String editOrder(Model model, @PathVariable("id") long id) {
        OrderDTO order = OrderMapper.INSTANCE.orderEntityToDTO(orderService.getOrderById(id));
        model.addAttribute(ORDER, order);
        model.addAttribute(PAYMENT_STATUS, PaymentStatus.values());
        model.addAttribute(ORDER_STATUS, OrderStatus.values());
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
     * CLIENT SIDE:
     **/

    @GetMapping("/self/all")
    @PreAuthorize("isAuthenticated()")
    public String showAllSelfOrders(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, value = 2)
                    Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @AuthenticationPrincipal User user) {
        // Settings of pagination:
        int currentPage = page.orElse(currentPageNormal);
        int pageSize = size.orElse(pageSizeNormal);
        Pageable allProductsPage = PageRequest.of(currentPage - 1, pageSize);

        // Pagination of all products

        UserDTO activeUser = userService.getUserByEmail(user.getUsername());
        long userId = activeUser.getId();
        Page<OrderDTO> userOrders = orderService.findByUserId(userId, allProductsPage);
        model.addAttribute(ACTIVE_USER, activeUser);
        model.addAttribute(ORDERS, userOrders);

        // Counting the number of page
        List<Integer> pageNumbers = getPagesCount(userOrders);
        model.addAttribute(PAGE_NUMBERS, pageNumbers);

        return "orders/allUserOrders";
    }

    @GetMapping("/self/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showSelfOrder(@PathVariable("id") long id, Model model) {
        OrderDTO order = OrderMapper.INSTANCE.orderEntityToDTO(orderService.getOrderById(id));
        AddressDTO address = AddressMapper.INSTANCE.addressEntityToDTO(order.getAddress());
        UserDTO user = order.getUser();
        model.addAttribute(ORDER, order);
        model.addAttribute(ADDRESS, address);
        model.addAttribute(USER, user);
        model.addAttribute(PRODUCTS, order.getProductsInOrder());
        return "orders/showOrder";
    }

    @GetMapping("/newOrder")
    @PreAuthorize("isAuthenticated()")
    public String showOrderConfirmPage(Model model,
                                       RedirectAttributes attributes,
                                       @ModelAttribute("cart") Cart cart,
                                       @AuthenticationPrincipal User user) {
        UserDTO activeUser = userService.getUserByEmail(user.getUsername());
        model.addAttribute(ACTIVE_USER, activeUser);
        model.addAttribute(DELIVERY_METHOD, DeliveryMethod.values());
        model.addAttribute(PAYMENT_METHOD, PaymentMethod.values());
        Set<AddressDTO> addressDTOSet = activeUser.getAddresses();
        model.addAttribute(ADDRESSES, addressDTOSet);
        attributes.addFlashAttribute(CART, cart);
        totalPrice = 0;
        for (ProductDTO prod : cart) {
            totalPrice += prod.getPrice();
        }
        model.addAttribute(TOTAL_PRICE, totalPrice);
        model.addAttribute(NEW_ORDER, new OrderDTO());
        return "orders/confirmNewOrderPage";
    }

    @PostMapping("/newOrder")
    @PreAuthorize("isAuthenticated()")
    public String createNewOrder(@ModelAttribute("order") @Valid OrderDTO orderDTO,
                                 @ModelAttribute("cart") Cart cart,
                                 @AuthenticationPrincipal User user) {
        orderDTO.setPaymentStatus(PaymentStatus.PENDING);
        orderDTO.setOrderStatus(OrderStatus.PENDING_PAYMENT);
        orderDTO.setUser(userService.getUserByEmail(user.getUsername()));
        orderService.createNewOrder(orderDTO, cart);
        totalPrice = 0;
        for (ProductDTO productInOrder : cart) {
            int leftInStock = productInOrder.getLeftInStock();
            if (leftInStock > 0)
                productService.reduceAmount(productInOrder.getId());
            else return "errors/noProduct";
        }
        cart.clear();
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
