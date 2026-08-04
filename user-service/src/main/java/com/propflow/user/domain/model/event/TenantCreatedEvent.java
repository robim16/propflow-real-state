package com.propflow.user.domain.model.event;

import com.propflow.user.domain.model.vo.ProfileStatus;

import java.time.Instant;

public record TenantCreatedEvent(
        String        tenantId,
        String        userId,
        ProfileStatus profileStatus,
        Instant occurredAt
) implements DomainEvent {

    @Override
    public String eventType() { return "tenant.created"; }

    @Override
    public Instant occurredAt() { return occurredAt; }
}
