package com.propflow.auth.application.usecase;

import com.propflow.auth.domain.model.TokenPair;
import com.propflow.auth.domain.port.in.RefreshTokenUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {
    @Override
    public Mono<TokenPair> execute(String refreshToken) {
        return null;
    }
}
