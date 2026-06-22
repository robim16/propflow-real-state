package com.propflow.auth.infrastructure.adapter.persistence;

import com.propflow.auth.domain.port.out.LoginAttemptPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class LoginAttemptAdapter implements LoginAttemptPort {
    @Override
    public Mono<Integer> incrementFailedAttempts(String email) {
        return null;
    }

    @Override
    public Mono<Void> resetAttempts(String email) {
        return null;
    }

    @Override
    public Mono<Boolean> isLocked(String email) {
        return null;
    }
}
