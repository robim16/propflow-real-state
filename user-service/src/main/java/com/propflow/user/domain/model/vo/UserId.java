package com.propflow.user.domain.model.vo;

import java.util.Objects;

public record UserId(String value) {

    public UserId {
        Objects.requireNonNull(value, "UserId no puede ser nulo");
        if (value.isBlank())
            throw new IllegalArgumentException("UserId no puede estar vacío");
    }

    public static UserId of(String value) {
        return new UserId(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
