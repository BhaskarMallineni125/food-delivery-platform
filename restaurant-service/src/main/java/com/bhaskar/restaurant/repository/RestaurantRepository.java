package com.bhaskar.restaurant.repository;

import com.bhaskar.restaurant.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByCuisineIgnoreCase(String cuisine);

    Page<Restaurant> findAll(Pageable pageable);

    List<Restaurant> findByNameContainingIgnoreCase(String name);
}
