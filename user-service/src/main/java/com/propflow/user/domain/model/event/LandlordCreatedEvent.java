package com.propflow.user.domain.model.event;

import com.propflow.user.domain.model.vo.ProfileStatus;


import java.time.Instant;

public record LandlordCreatedEvent(
        String        landlordId,
        String        userId,
        ProfileStatus profileStatus,
        Instant       occurredAt
) implements DomainEvent {

    @Override
    public String eventType() {
        return "landlord.created";
    }

    @Override
    public Instant occurredAt() {
        return occurredAt;
    }
}
