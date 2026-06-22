package com.propflow.user.domain.exception;

public class ProfileAlreadyExistsException extends RuntimeException {
    public ProfileAlreadyExistsException(UserId userId) {
        super("El usuario " + userId.value() + " ya tiene un perfil de arrendador");
    }
}
