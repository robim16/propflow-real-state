package com.propflow.auth.infrastructure.adapter.kafka;

import com.propflow.auth.domain.port.out.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventPublisher implements DomainEventPublisher {
    @Override
    public Mono<Void> publish(String topic, String key, Object event) {
        return null;
    }
}
