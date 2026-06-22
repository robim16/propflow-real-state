package com.propflow.apigateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

@Configuration
public class RedisRateLimiterConfig {

    @Bean
    @Primary
    public KeyResolver ipKeyResolver() {
        return  exchange -> {
            String forwarded = exchange.getRequest()
                    .getHeaders().getFirst("X-Forwarded-For");

            String ip = (forwarded != null && forwarded.isBlank())
             ? forwarded.split(",")[0].trim()
                : exchange.getRequest().getRemoteAddress() != null
                    ? exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
                    : "unknown";
            return Mono.just(ip);
        };
    }

}
