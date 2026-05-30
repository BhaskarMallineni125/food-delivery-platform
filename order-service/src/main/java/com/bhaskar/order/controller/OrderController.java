package com.bhaskar.order.controller;

import com.bhaskar.order.dto.ApiResponse;
import com.bhaskar.order.dto.CreateOrderRequest;
import com.bhaskar.order.dto.OrderResponse;
import com.bhaskar.order.dto.UpdateOrderStatusRequest;
import com.bhaskar.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>>
    createOrder(
            @Valid
            @RequestBody
            CreateOrderRequest request
    ) {

        OrderResponse orderResponse =
                orderService.createOrder(request);

        ApiResponse<OrderResponse> response =
                ApiResponse.<OrderResponse>builder()
                        .success(true)
                        .message("Order created successfully")
                        .data(orderResponse)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>>
    getOrderById(
            @PathVariable("id") Long id
    ) {

        OrderResponse orderResponse =
                orderService.getOrderById(id);

        ApiResponse<OrderResponse> response =
                ApiResponse.<OrderResponse>builder()
                        .success(true)
                        .message("Order fetched successfully")
                        .data(orderResponse)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<Page<OrderResponse>>>
    getOrdersByCustomer(

            @PathVariable("cutomer") Long customerId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        Page<OrderResponse> orders =
                orderService.getOrdersByCustomer(
                        customerId,
                        page,
                        size
                );

        ApiResponse<Page<OrderResponse>> response =
                ApiResponse.<Page<OrderResponse>>builder()
                        .success(true)
                        .message("Orders fetched successfully")
                        .data(orders)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrderResponse>>
    updateStatus(

            @PathVariable("id") Long id,

            @Valid@RequestBody
            UpdateOrderStatusRequest request
    ) {

        OrderResponse orderResponse =
                orderService.updateOrderStatusRequest(
                        id,
                        request.getStatus()
                );

        ApiResponse<OrderResponse> response =
                ApiResponse.<OrderResponse>builder()
                        .success(true)
                        .message("Order status updated successfully")
                        .data(orderResponse)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }
}