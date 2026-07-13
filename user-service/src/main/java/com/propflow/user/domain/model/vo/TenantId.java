package com.propflow.user.domain.model.vo;

import java.util.UUID;

public record TenantId(String value) {

    public TenantId {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("TenantId no puede ser vacío");
    }

    public static TenantId generate() {
        return new TenantId(UUID.randomUUID().toString());
    }

    public static TenantId of(String value) { return new TenantId(value); }

    @Override
    public String toString() { return value; }
}
