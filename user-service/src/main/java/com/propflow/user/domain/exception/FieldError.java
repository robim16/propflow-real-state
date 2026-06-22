package com.propflow.user.domain.exception;

public record FieldError(
        String field,
        String message
) {}