package org.example.FinalProject.services;

import lombok.RequiredArgsConstructor;
import org.example.FinalProject.dto.Cart;
import org.example.FinalProject.dto.OrderDTO;
import org.example.FinalProject.dto.ProductDTO;
import org.example.FinalProject.mappers.OrderMapper;
import org.example.FinalProject.mappers.ProductMapper;
import org.example.FinalProject.models.OrderEntity;
import org.example.FinalProject.models.ProductEntity;
import org.example.FinalProject.repositories.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Page<OrderDTO> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderMapper.INSTANCE::orderEntityToDTO);
    }

    public Page<OrderDTO> findByUserId(long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable).map(OrderMapper.INSTANCE::orderEntityToDTO);
    }

    public OrderEntity getOrderById(long id) {
        return orderRepository.findById(id).orElse(null);
    }

    /**
     * Method for creating new order and adding it to DB.
     * Here we add all products from cart to ArrayList and add it to orderEntity. After if we save orderEntity in DB.
     *
     * @param orderDTO
     * @param cart
     */
    public OrderEntity createNewOrder(OrderDTO orderDTO, Cart cart) {
        OrderEntity orderEntity = OrderMapper.INSTANCE.orderDTOToEntity(orderDTO);
        List<ProductEntity> productEntities = new ArrayList<>();
        for (ProductDTO prodDto : cart) {
            productEntities.add(ProductMapper.INSTANCE.productDTOToEntity(prodDto));
        }
        orderEntity.setProductsInOrder(productEntities);
        return orderRepository.save(orderEntity);
    }

    /**
     * Method for updating order in DB. Here we get parameters of sql-request and place them in right order.
     *
     * @param id
     * @param changedOrder
     */
    public void updateOrder(long id, OrderDTO changedOrder) {
        String paymentStatus = String.valueOf(changedOrder.getPaymentStatus());
        String orderStatus = String.valueOf(changedOrder.getOrderStatus());

        orderRepository.updateOrder(paymentStatus, orderStatus, id);
    }
}
