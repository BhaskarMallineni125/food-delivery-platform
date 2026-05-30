package com.bhaskar.order.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemResponse {

    private String itemName;

    private Integer quantity;

    private BigDecimal price;
}