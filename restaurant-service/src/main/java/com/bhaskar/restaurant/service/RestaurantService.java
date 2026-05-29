package com.bhaskar.restaurant.service;

import com.bhaskar.restaurant.dto.CreateRestaurantRequest;
import com.bhaskar.restaurant.dto.RestaurantResponse;
import com.bhaskar.restaurant.entity.Restaurant;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RestaurantService {

    Restaurant createRestaurant(CreateRestaurantRequest request);

    List<Restaurant> getAllRestaurants();

    List<Restaurant> getRestaurantsByCuisine(String cuisine);

    Page<RestaurantResponse> getRestaurants(
            int page,
            int size
    );

    List<RestaurantResponse> search(String keyword);

}
