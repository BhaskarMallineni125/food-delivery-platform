package com.bhaskar.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRestaurantRequest {

    @NotBlank
    private String name;

    private String cuisine;

    private String address;
}
