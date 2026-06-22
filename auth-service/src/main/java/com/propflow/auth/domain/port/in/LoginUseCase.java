package com.propflow.auth.domain.port.in;

import com.propflow.auth.domain.model.AuthCredentials;
import com.propflow.auth.domain.model.TokenPair;
import reactor.core.publisher.Mono;

public interface LoginUseCase {
    Mono<TokenPair> execute(AuthCredentials credentials);
}
