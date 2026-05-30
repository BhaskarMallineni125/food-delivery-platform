package com.bhaskar.order.event;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private Long orderId;

    private Long customerId;

    private Long restaurantId;

    private BigDecimal totalAmount;
}