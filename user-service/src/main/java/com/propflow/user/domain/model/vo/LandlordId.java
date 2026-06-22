package com.propflow.user.domain.model.vo;


import java.util.UUID;

public record LandlordId(String value) {

    public LandlordId {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("LandlordId no puede ser vacío");
    }

    public static LandlordId generate() {
        return new LandlordId(UUID.randomUUID().toString());
    }

    public static LandlordId of(String value) {
        return new LandlordId(value);
    }

    @Override
    public String toString() { return value; }
}
