package org.example.FinalProject.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.FinalProject.models.CategoryEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotEmpty(message = "Title should not be empty")
    @NotBlank(message = "Title should not be empty")
    @Size(min = 2, max = 55, message = "Title must contain from 2 to 55 characters")
    private String title;

    @DecimalMin(value = "0.1", message = "Can not be less than 0.1")
    @NotNull(message = "Please, specify the price")
    private double price;

    @Min(value = 0, message = "Can not be less than 0")
    @NotNull(message = "Please, specify the count")
    private int leftInStock;

    private CategoryEntity category;
}
