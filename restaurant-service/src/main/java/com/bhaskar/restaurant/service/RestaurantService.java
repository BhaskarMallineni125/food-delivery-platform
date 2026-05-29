package com.bhaskar.restaurant.service;

import com.bhaskar.restaurant.dto.CreateRestaurantRequest;
import com.bhaskar.restaurant.entity.Restaurant;

import java.util.List;

public interface RestaurantService {

    Restaurant createRestaurant(CreateRestaurantRequest request);

    List<Restaurant> getAllRestaurants();

    List<Restaurant> getRestaurantsByCuisine(String cuisine);

}
