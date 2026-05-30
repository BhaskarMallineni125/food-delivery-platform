package com.bhaskar.restaurant.service.impl;

import com.bhaskar.restaurant.dto.CreateRestaurantRequest;
import com.bhaskar.restaurant.dto.RestaurantResponse;
import com.bhaskar.restaurant.entity.Restaurant;
import com.bhaskar.restaurant.mapper.RestaurantMapper;
import com.bhaskar.restaurant.repository.RestaurantRepository;
import com.bhaskar.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl
        implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    @Caching(
            evict = {
                    @CacheEvict(
                            value = "restaurantsByCuisine",
                            allEntries = true
                    )
            }
    )
    public RestaurantResponse createRestaurant(
            CreateRestaurantRequest request
    ) {

        Restaurant restaurant =
                Restaurant.builder()
                        .name(request.getName())
                        .cuisine(request.getCuisine())
                        .address(request.getAddress())
                        .rating(0.0)
                        .active(true)
                        .build();

        Restaurant savedRestaurant =
                restaurantRepository.save(restaurant);

        log.info(
                "Restaurant created. Clearing restaurant caches."
        );

        return RestaurantMapper.toResponse(savedRestaurant);
    }

    @Override
    @Cacheable(
            value = "restaurantsByCuisine",
            key = "#p0.toLowerCase()"
    )
    public List<RestaurantResponse> getRestaurantsByCuisine(
            String cuisine
    ) {

        log.info(
                "CACHE MISS -> Fetching cuisine {} from database",
                cuisine
        );

        return restaurantRepository
                .findByCuisineIgnoreCase(cuisine)
                .stream()
                .map(RestaurantMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<RestaurantResponse> getRestaurants(
            int page,
            int size
    ) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("rating")
                                .descending()
                );

        return restaurantRepository
                .findAll(pageable)
                .map(RestaurantMapper::toResponse);
    }

    @Override
    public List<RestaurantResponse> search(
            String keyword
    ) {

        log.info(
                "Searching restaurants with keyword {}",
                keyword
        );

        return restaurantRepository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(RestaurantMapper::toResponse)
                .collect(Collectors.toList());
    }
}