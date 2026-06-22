package com.propflow.auth.application.usecase;


import com.propflow.auth.domain.model.User;
import com.propflow.auth.domain.port.in.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {
    @Override
    public Mono<User> execute(Command command) {
        return null;
    }
}
