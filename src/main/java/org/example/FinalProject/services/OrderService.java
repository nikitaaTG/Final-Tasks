package org.example.FinalProject.services;

import java.util.ArrayList;
import java.util.List;

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

import lombok.RequiredArgsConstructor;

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

    public OrderEntity createNewOrder(OrderDTO orderDTO, Cart cart) {
        OrderEntity orderEntity = OrderMapper.INSTANCE.orderDTOToEntity(orderDTO);

        List<ProductEntity> productEntities = new ArrayList<>();
        for (ProductDTO prodDto : cart) {
            productEntities.add(ProductMapper.INSTANCE.productDTOToEntity(
                    prodDto)); // FIXME: а еще в маппере можно создать метод, туДто, который бы принимал лист, он тоже бы автоматически сгенерировался
        }

        orderEntity.setProductsInOrder(productEntities);
        return orderRepository.save(orderEntity);
    }

    public void updateOrder(long id, OrderDTO changedOrder) {
        String paymentStatus = String.valueOf(changedOrder.getPaymentStatus());
        String orderStatus = String.valueOf(changedOrder.getOrderStatus());

        orderRepository.updateOrder(paymentStatus, orderStatus, id);
    }
}
