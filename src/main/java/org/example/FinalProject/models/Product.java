package org.example.FinalProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "title")
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 55, message = "Title must contain from 2 to 55 characters")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    @NotEmpty(message = "Description should not be empty")
    @Size(min = 10, max = 255, message = "Description must contain from 10 to 255 characters")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "city")
    @NotEmpty(message = "City should not be empty")
    @Size(min = 2, max = 55, message = "Name of city must contain from 2 to 55 characters")
    private String city;


    @Column(name = "author")
    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 55, message = "Name of author must contain from 2 to 55 characters")
    private String author;
}
