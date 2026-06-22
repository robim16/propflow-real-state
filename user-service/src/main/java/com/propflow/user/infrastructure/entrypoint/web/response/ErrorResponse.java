package com.propflow.user.infrastructure.entrypoint.web.response;

import com.propflow.user.domain.exception.FieldError;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        String       code,
        String       message,
        List<FieldError> errors,    // null si no es error de validación
        Instant timestamp
) {
    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(code, message, null, Instant.now());
    }

    public static ErrorResponse ofValidation(List<FieldError> errors) {
        return new ErrorResponse(
                "VALIDATION_ERROR",
                "La solicitud contiene campos inválidos",
                errors,
                Instant.now()
        );
    }
}