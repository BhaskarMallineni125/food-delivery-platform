package com.bhaskar.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurants",
        indexes = {
                @Index(
                        name = "idx_restaurant_cuisine",
                        columnList = "cuisine"
                )
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String cuisine;

    private String address;

    private double rating;

    private boolean active;
}
