package com.bhaskar.order.mapper;

import com.bhaskar.order.dto.OrderItemResponse;
import com.bhaskar.order.dto.OrderResponse;
import com.bhaskar.order.entity.Order;

import java.util.stream.Collectors;

public final class OrderMapper {

    private OrderMapper() {
    }

    public static OrderResponse toResponse(
            Order order
    ) {

        return OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .restaurantId(order.getRestaurantId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(
                        order.getOrderItems()
                                .stream()
                                .map(item ->
                                        OrderItemResponse.builder()
                                                .itemName(item.getItemName())
                                                .quantity(item.getQuantity())
                                                .price(item.getPrice())
                                                .build()
                                )
                                .collect(Collectors.toList())
                )
                .build();
    }
}