package com.propflow.user.domain.exception;

import com.propflow.user.domain.model.vo.LandlordId;

public class LandlordNotFoundException extends RuntimeException {
    public LandlordNotFoundException(LandlordId landlordId) {
        super("lanlord with id " + String.valueOf(landlordId) + " not found");
    }
}
