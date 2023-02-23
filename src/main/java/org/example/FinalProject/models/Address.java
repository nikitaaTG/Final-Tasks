package org.example.FinalProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "country")
    @NotEmpty(message = "Country is empty")
    @Size(min = 2, max = 55, message = "Name of country must contain from 2 to 55 characters")
    private String country;

    @Column(name = "city")
    @NotEmpty(message = "City is empty")
    @Size(min = 2, max = 55, message = "Name of city must contain from 2 to 55 characters")
    private String city;

    @Column(name = "post index")
    @NotEmpty(message = "Post index is empty")
    @Size(min = 5, max = 5, message = "Post index must contain 5 digits")
    private int postIndex;

    @Column(name = "street")
    @NotEmpty(message = "Street is empty")
    @Size(min = 1, max = 99, message = "Name of city must contain from 2 to 55 characters")
    private String street;

    @Column(name = "home")
    @NotEmpty(message = "Home is empty")
    private int home;

    @Column(name = "apartment")
    @NotEmpty(message = "Apartment is empty")
    private int apartment;

    @Column(insertable=false, updatable=false,name = "user_id")
    @NotNull
    private int userId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
