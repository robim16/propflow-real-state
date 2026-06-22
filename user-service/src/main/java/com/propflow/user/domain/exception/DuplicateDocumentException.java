package com.propflow.user.domain.exception;

public class DuplicateDocumentException extends RuntimeException {
    public DuplicateDocumentException(String documentNumber) {
        super("Ya existe un arrendador con el documento " + documentNumber);
    }
}
