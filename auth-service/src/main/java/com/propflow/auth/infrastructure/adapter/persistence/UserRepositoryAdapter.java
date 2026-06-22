package com.propflow.auth.infrastructure.adapter.persistence;

import com.propflow.auth.domain.model.User;
import com.propflow.auth.domain.port.out.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class UserRepositoryAdapter implements UserRepository {
    @Override
    public Mono<User> save(User user) {
        return null;
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return null;
    }

    @Override
    public Mono<User> findById(UUID id) {
        return null;
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return null;
    }

    @Override
    public Mono<User> update(User user) {
        return null;
    }

    @Override
    public Flux<User> findAll() {
        return null;
    }
}
