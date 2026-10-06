package com.propflow.user.domain.model.vo;

import java.util.UUID;

public record AdvisorId(String value) {
    public AdvisorId {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("AdvisorId no puede ser vacío");
    }

    public static AdvisorId generate() { return new AdvisorId(UUID.randomUUID().toString()); }
    public static AdvisorId of(String value) { return new AdvisorId(value); }

    @Override
    public String toString() { return value; }
}
