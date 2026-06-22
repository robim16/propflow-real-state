package com.propflow.auth.infrastructure.adapter.persistence;

import com.propflow.auth.domain.port.out.TokenBlacklistPort;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class TokenBlacklistAdapter implements TokenBlacklistPort {
    @Override
    public Mono<Void> blacklist(String token, Duration ttl) {
        return null;
    }

    @Override
    public Mono<Boolean> isBlacklisted(String token) {
        return null;
    }
}
