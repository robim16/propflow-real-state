package com.propflow.auth.domain.port.out;

import com.propflow.auth.domain.model.TokenPair;
import com.propflow.auth.domain.model.User;

import java.util.UUID;

public interface TokenPort {

    /** Genera un par access/refresh token para el usuario dado. */
    TokenPair generateTokenPair(User user);

    /** Extrae el userId del subject del token. */
    UUID extractUserId(String token);

    /** Extrae el tipo del token ("access" o "refresh"). */
    String extractType(String token);

    /** Devuelve true si el token tiene firma válida y no ha expirado. */
    boolean isValid(String token);
}
