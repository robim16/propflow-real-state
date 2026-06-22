package com.propflow.user.domain.exception;

import java.util.List;

public class ValidationException extends RuntimeException {

    private final List<FieldError> errors;

    public ValidationException(List<FieldError> errors) {
        super("La solicitud contiene campos inválidos");
        this.errors = errors;
    }

    public List<FieldError> getErrors() {
        return errors;
    }
}
