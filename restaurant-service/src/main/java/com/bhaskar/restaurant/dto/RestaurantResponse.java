package com.bhaskar.restaurant.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RestaurantResponse implements Serializable {

    private Long id;

    private String name;

    private String cuisine;

    private String address;

    private Double rating;
}
