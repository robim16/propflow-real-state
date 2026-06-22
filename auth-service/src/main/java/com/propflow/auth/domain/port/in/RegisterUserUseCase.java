package com.propflow.auth.domain.port.in;

import com.propflow.auth.domain.model.User;
import com.propflow.auth.domain.model.UserRole;
import reactor.core.publisher.Mono;

public interface RegisterUserUseCase {
    record Command(String name, String email, String password, UserRole role) {}
    Mono<User> execute(Command command);
}
