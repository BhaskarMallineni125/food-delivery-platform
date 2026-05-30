package com.bhaskar.order.service.impl;

import com.bhaskar.order.dto.CreateOrderRequest;
import com.bhaskar.order.dto.OrderItemRequest;
import com.bhaskar.order.dto.OrderResponse;
import com.bhaskar.order.entity.Order;
import com.bhaskar.order.entity.OrderItem;
import com.bhaskar.order.entity.OrderStatus;
import com.bhaskar.order.event.OrderCreatedEvent;
import com.bhaskar.order.exception.OrderNotFoundException;
import com.bhaskar.order.mapper.OrderMapper;
import com.bhaskar.order.producer.OrderEventProducer;
import com.bhaskar.order.repository.OrderRepository;
import com.bhaskar.order.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl
        implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderEventProducer orderEventProducer;

    @Override
    @Transactional
    public OrderResponse createOrder(
            CreateOrderRequest request
    ) {

        BigDecimal totalAmount =
                request.getItems()
                        .stream()
                        .map(item ->
                                item.getPrice()
                                        .multiply(
                                                BigDecimal.valueOf(
                                                        item.getQuantity()
                                                )
                                        )
                        )
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        Order order =
                Order.builder()
                        .customerId(
                                request.getCustomerId()
                        )
                        .restaurantId(
                                request.getRestaurantId()
                        )
                        .status(
                                OrderStatus.CREATED
                        )
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .totalAmount(
                                totalAmount
                        )
                        .build();

        request.getItems()
                .forEach(itemRequest ->
                        order.getOrderItems()
                                .add(
                                        buildOrderItem(
                                                itemRequest,
                                                order
                                        )
                                )
                );

        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event =
                OrderCreatedEvent.builder()
                        .orderId(
                                savedOrder.getId()
                        )
                        .customerId(
                                savedOrder.getCustomerId()
                        )
                        .restaurantId(
                                savedOrder.getRestaurantId()
                        )
                        .totalAmount(
                                savedOrder.getTotalAmount()
                        )
                        .build();

        orderEventProducer
                .publishOrderCreatedEvent(event);

        return OrderMapper.toResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(Long id) {

        Order order =
                orderRepository.findById(id)
                        .orElseThrow(
                                () -> new OrderNotFoundException(
                                        "Order not found with id " + id
                                )
                        );

        return OrderMapper.toResponse(order);
    }

    @Override
    public Page<OrderResponse>
    getOrdersByCustomer(
            Long customerId,
            int page,
            int size
    ) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("createdAt")
                                .descending()
                );

        return orderRepository
                .findByCustomerId(
                        customerId,
                        pageable
                )
                .map(OrderMapper::toResponse);
    }

    private OrderItem buildOrderItem(
            OrderItemRequest request,
            Order order
    ) {

        return OrderItem.builder()
                .itemName(
                        request.getItemName()
                )
                .quantity(
                        request.getQuantity()
                )
                .price(
                        request.getPrice()
                )
                .order(order)
                .build();
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatusRequest(
            Long orderId,
            OrderStatus status
    ) {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(
                                () -> new OrderNotFoundException(
                                        "Order not found"
                                )
                        );

        order.setStatus(status);

        order.setUpdatedAt(
                LocalDateTime.now()
        );

        return OrderMapper.toResponse(
                orderRepository.save(order)
        );
    }
}