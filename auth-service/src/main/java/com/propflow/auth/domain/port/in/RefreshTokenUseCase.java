package com.propflow.auth.domain.port.in;

import com.propflow.auth.domain.model.TokenPair;
import reactor.core.publisher.Mono;

public interface RefreshTokenUseCase {
    Mono<TokenPair> execute(String refreshToken);
}
