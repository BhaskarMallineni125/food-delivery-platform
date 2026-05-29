package com.bhaskar.restaurant.mapper;

import com.bhaskar.restaurant.dto.RestaurantResponse;
import com.bhaskar.restaurant.entity.Restaurant;

public class RestaurantMapper {

    private RestaurantMapper() {
    }

    public static RestaurantResponse toResponse(
            Restaurant restaurant
    ) {

        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .cuisine(restaurant.getCuisine())
                .address(restaurant.getAddress())
                .rating(restaurant.getRating())
                .build();
    }
}