package com.propflow.user.domain.exception;

public class DuplicateBankAccountException extends RuntimeException {

    public DuplicateBankAccountException(String rawAccountNumber) {
        super("Ya existe un arrendador con los últimos 4 dígitos: "
                + rawAccountNumber.substring(rawAccountNumber.length() - 4));
    }
}