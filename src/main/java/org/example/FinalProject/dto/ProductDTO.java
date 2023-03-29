package org.example.FinalProject.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Title should not be empty")
    @Size(min = 2, max = 55, message = "Title must contain from 2 to 55 characters")
    private String title;

    @DecimalMin(value = "0.1", message = "Can not be less than 0.1")
    private double price;

    @NotBlank(message = "Description should not be empty")
    @Size(min = 2, max = 255, message = "Description must contain from 2 to 255 characters")
    private String description;

    @Min(value = 0, message = "Can not be less than 0")
    private int leftInStock;

    private CategoryEntity category;
}
