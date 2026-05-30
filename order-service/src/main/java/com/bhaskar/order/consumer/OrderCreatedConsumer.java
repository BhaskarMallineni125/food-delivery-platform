package com.bhaskar.order.consumer;

import com.bhaskar.order.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderCreatedConsumer {

    @KafkaListener(
            topics = "order-created",
            groupId = "payment-group"
    )
    public void consume(
            OrderCreatedEvent event
    ) {

        log.info(
                "Received OrderCreatedEvent : {}",
                event
        );

        log.info(
                "Simulating payment processing..."
        );
    }
}