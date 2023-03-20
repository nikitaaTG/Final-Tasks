package org.example.FinalProject.services;

import lombok.RequiredArgsConstructor;
import org.example.FinalProject.dto.Cart;
import org.example.FinalProject.dto.OrderDTO;
import org.example.FinalProject.dto.ProductDTO;
import org.example.FinalProject.mappers.OrderMapper;
import org.example.FinalProject.mappers.ProductMapper;
import org.example.FinalProject.models.OrderEntity;
import org.example.FinalProject.models.ProductEntity;
import org.example.FinalProject.repositories.CategoryRepository;
import org.example.FinalProject.repositories.OrderRepository;
import org.example.FinalProject.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final OrderRepository orderRepository;

    public Page<OrderDTO> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderMapper.INSTANCE::orderEntityToDTO);
    }

    public OrderEntity getOrderById(long id) {
        return orderRepository.getReferenceById(id);
    }

    public OrderEntity createNewOrder(OrderDTO orderDTO, Cart cart) {
        OrderEntity orderEntity = OrderMapper.INSTANCE.orderDTOToEntity(orderDTO);

        ArrayList<ProductEntity> productEntities = new ArrayList<>();
        for (ProductDTO prodDto:cart) {
            productEntities.add(ProductMapper.INSTANCE.productDTOToEntity(prodDto));
        }

        orderEntity.setProductsInOrder(productEntities);
        return orderRepository.save(orderEntity);
    }
}
