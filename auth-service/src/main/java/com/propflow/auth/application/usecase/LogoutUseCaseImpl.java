package com.propflow.auth.application.usecase;

import com.propflow.auth.domain.port.in.LogoutUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutUseCaseImpl implements LogoutUseCase {
    @Override
    public Mono<Void> execute(String refreshToken) {
        return null;
    }
}
