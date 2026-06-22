package com.propflow.user.domain.model.event;

import java.time.Instant;

public interface DomainEvent {
    String eventType();
    Instant occurredAt();
}
