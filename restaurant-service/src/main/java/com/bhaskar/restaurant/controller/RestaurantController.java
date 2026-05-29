package com.bhaskar.restaurant.controller;

import com.bhaskar.restaurant.dto.CreateRestaurantRequest;
import com.bhaskar.restaurant.dto.RestaurantResponse;
import com.bhaskar.restaurant.entity.Restaurant;
import com.bhaskar.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(
            @Valid @RequestBody
            CreateRestaurantRequest request
    ) {

        return ResponseEntity.ok(
                restaurantService.createRestaurant(request)
        );
    }

    @GetMapping
    public ResponseEntity<Page<RestaurantResponse>>
    getRestaurants(

            @RequestParam(name = "page", defaultValue = "0")
            int page,

            @RequestParam(name = "size", defaultValue = "10")
            int size
    ) {

        return ResponseEntity.ok(
                restaurantService
                        .getRestaurants(page, size)
        );
    }

    @GetMapping("/cuisine/{cuisine}")
    public ResponseEntity<List<Restaurant>>
    getByCuisine(
            @PathVariable("cuisine") String cuisine
    ) {

        return ResponseEntity.ok(
                restaurantService
                        .getRestaurantsByCuisine(cuisine)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<RestaurantResponse>> search(
            @RequestParam("keyword") String keyword
    ) {

        return ResponseEntity.ok(
                restaurantService.search(keyword)
        );
    }

}