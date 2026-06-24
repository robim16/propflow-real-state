package com.propflow.user.domain.exception;

public class LandlordNotFoundException extends RuntimeException {
    public LandlordNotFoundException(String message) {
        super(message);
    }
}
