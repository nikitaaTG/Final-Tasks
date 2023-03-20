package org.example.FinalProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.FinalProject.enums.DeliveryMethod;
import org.example.FinalProject.enums.OrderStatus;
import org.example.FinalProject.enums.PaymentMethod;
import org.example.FinalProject.enums.PaymentStatus;
import org.example.FinalProject.models.AddressEntity;
import org.example.FinalProject.models.ProductEntity;
import org.example.FinalProject.models.UserEntity;

import java.util.ArrayList;

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

    private AddressEntity address;

    private UserEntity user;

    private ArrayList<ProductEntity> productsInOrder;
}
