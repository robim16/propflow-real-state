package com.propflow.auth.infrastructure.adapter.persistence;

import com.propflow.auth.domain.port.out.EmailVerificationPort;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class EmailVerificationAdapter implements EmailVerificationPort {
    @Override
    public Mono<String> createVerificationToken(UUID userId) {
        return null;
    }

    @Override
    public Mono<UUID> validateToken(String token) {
        return null;
    }

    @Override
    public Mono<Void> deleteToken(String token) {
        return null;
    }
}
