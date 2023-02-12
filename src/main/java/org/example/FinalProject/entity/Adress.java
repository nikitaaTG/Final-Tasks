package org.example.FinalProject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "adress")
public class Adress {
    public Adress() {}

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

    @Column(name = "client_id")
    @NotNull
    private int clientId;



    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;


    public  Client getClient() {
        return  client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostIndex() {
        return postIndex;
    }

    public void setPostIndex(int postIndex) {
        this.postIndex = postIndex;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    public int getApartment() {
        return apartment;
    }

    public void setApartment(int apartment) {
        this.apartment = apartment;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
