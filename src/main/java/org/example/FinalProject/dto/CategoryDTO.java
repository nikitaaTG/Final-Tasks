package org.example.FinalProject.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

    public CategoryDTO(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotBlank(message = "Name of category is empty") @NotNull(message = "Name of category should not be empty")
    // FIXME: нотбланк покрывает нотналл
    @Size(min = 2, max = 45, message = "Name of category must contain from 2 to 45 characters")
    private String name;


}
