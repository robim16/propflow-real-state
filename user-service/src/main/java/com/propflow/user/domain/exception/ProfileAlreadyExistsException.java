package com.propflow.user.domain.exception;

import com.propflow.user.domain.model.vo.UserId;

public class ProfileAlreadyExistsException extends RuntimeException {
    public ProfileAlreadyExistsException(UserId userId) {
        super("El usuario " + userId.value() + " ya tiene un perfil de arrendador");
    }
}
