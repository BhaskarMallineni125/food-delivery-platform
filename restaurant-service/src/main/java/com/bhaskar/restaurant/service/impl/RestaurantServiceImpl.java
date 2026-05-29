package com.bhaskar.restaurant.service.impl;

import com.bhaskar.restaurant.dto.CreateRestaurantRequest;
import com.bhaskar.restaurant.dto.RestaurantResponse;
import com.bhaskar.restaurant.entity.Restaurant;
import com.bhaskar.restaurant.mapper.RestaurantMapper;
import com.bhaskar.restaurant.repository.RestaurantRepository;
import com.bhaskar.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl
        implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Restaurant createRestaurant(
            CreateRestaurantRequest request
    ) {

        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .cuisine(request.getCuisine())
                .address(request.getAddress())
                .rating(0.0)
                .active(true)
                .build();

        return restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> getRestaurantsByCuisine(
            String cuisine
    ) {

        return restaurantRepository
                .findByCuisineIgnoreCase(cuisine);
    }

    @Override
    public Page<RestaurantResponse> getRestaurants(
            int page,
            int size
    ) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by("rating")
                        .descending());

        return restaurantRepository
                .findAll(pageable)
                .map(RestaurantMapper::toResponse);
    }

    @Override
    public List<RestaurantResponse> search(
            String keyword
    ) {

        return restaurantRepository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(RestaurantMapper::toResponse)
                .collect(Collectors.toList());
    }

}