package com.bhaskar.order.producer;

import com.bhaskar.order.event.OrderCreatedEvent;
import com.bhaskar.order.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventProducer {

    private final
    KafkaTemplate<String, OrderCreatedEvent>
            kafkaTemplate;

    public void publishOrderCreatedEvent(
            OrderCreatedEvent event
    ) {

        log.info(
                "Publishing OrderCreatedEvent for order {}",
                event.getOrderId()
        );

        kafkaTemplate.send(
                KafkaTopics.ORDER_CREATED,
                String.valueOf(event.getOrderId()),
                event
        );
    }
}