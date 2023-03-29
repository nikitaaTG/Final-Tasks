package org.example.FinalProject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDTO {


    private Long id;


    @NotBlank(message = "Country is empty")
    @Size(min = 2, max = 55, message = "Name of country must contain from 2 to 55 characters")
    private String country;

    @NotBlank(message = "City is empty")
    @Size(min = 2, max = 55, message = "Name of city must contain from 2 to 55 characters")
    private String city;

    @NotNull(message = "Post index is empty")
    private int postIndex;

    @NotBlank(message = "Street is empty")
    @Size(min = 1, max = 99, message = "Name of city must contain from 2 to 55 characters")
    private String street;

    @NotNull(message = "Home number is empty")
    private int home;

    private int apartment;

    @NotNull
    private long userId;

}
