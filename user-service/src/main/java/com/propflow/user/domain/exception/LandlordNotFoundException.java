package com.propflow.user.domain.exception;

import com.propflow.user.domain.model.vo.LandlordId;
import com.propflow.user.domain.model.vo.UserId;

public class LandlordNotFoundException extends RuntimeException {
    public LandlordNotFoundException(LandlordId landlordId) {
        super("lanlord with id " + String.valueOf(landlordId) + " not found");
    }

    public LandlordNotFoundException(UserId userId) {
        super("landlord with id " + String.valueOf(userId) + " not found");
    }
}
