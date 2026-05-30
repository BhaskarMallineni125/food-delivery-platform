package com.bhaskar.order.service;

import com.bhaskar.order.dto.CreateOrderRequest;
import com.bhaskar.order.dto.OrderResponse;
import com.bhaskar.order.entity.OrderStatus;
import org.springframework.data.domain.Page;

public interface OrderService {

    OrderResponse createOrder(
            CreateOrderRequest request
    );

    OrderResponse getOrderById(Long id);

    Page<OrderResponse> getOrdersByCustomer(
            Long customerId,
            int page,
            int size
    );

    OrderResponse updateOrderStatusRequest(
            Long orderId,
            OrderStatus status
    );
}