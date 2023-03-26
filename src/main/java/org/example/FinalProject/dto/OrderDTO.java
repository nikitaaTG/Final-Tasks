package org.example.FinalProject.dto;

import java.util.List;

import org.example.FinalProject.enums.DeliveryMethod;
import org.example.FinalProject.enums.OrderStatus;
import org.example.FinalProject.enums.PaymentMethod;
import org.example.FinalProject.enums.PaymentStatus;
import org.example.FinalProject.models.AddressEntity;
import org.example.FinalProject.models.ProductEntity;
import org.example.FinalProject.models.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private long id;

    private PaymentMethod paymentMethod;

    private DeliveryMethod deliveryMethod;

    private PaymentStatus paymentStatus;

    private OrderStatus orderStatus;

    private AddressEntity address; // FIXME: никаких энтити в дто, должны быть дто внутри)

    private UserEntity user;

    private List<ProductEntity> productsInOrder;
}
