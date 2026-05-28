package com.bhaskar.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class RequestTimingFilter
        implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(
            org.springframework.web.server.ServerWebExchange exchange,
            org.springframework.cloud.gateway.filter.GatewayFilterChain chain
    ) {

        long startTime = System.currentTimeMillis();

        return chain.filter(exchange)
                .then(
                        Mono.fromRunnable(() -> {

                            long duration =
                                    System.currentTimeMillis()
                                            - startTime;

                            log.info(
                                    "Request completed in {} ms",
                                    duration
                            );
                        })
                );
    }

    @Override
    public int getOrder() {
        return 2;
    }

}