package org.example.FinalProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 55, message = "Title must contain from 2 to 55 characters")
    private String title;

    @Column(name = "price")
    @NotNull(message = "Please, specify the price")
    private double price;

    @Column(name = "left_in_stock")
    @NotNull(message = "Please, specify the count")
    private int leftInStock;

    @Column(name = "description")
    @NotEmpty(message = "Description should not be empty")
    @Size(min = 2, max = 55, message = "Description must contain from 2 to 55 characters")
    private String description;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_in_order",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<OrderEntity> ordersWithProduct;
}
