package com.bhaskar.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    @NotNull
    private Long customerId;

    @NotNull
    private Long RestaurantId;

    @Valid
    @NotEmpty
    private List<OrderItemRequest> items;

}
