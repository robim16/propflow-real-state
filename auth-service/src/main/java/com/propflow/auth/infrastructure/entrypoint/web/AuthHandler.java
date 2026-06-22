package com.propflow.auth.infrastructure.entrypoint.web;

import com.propflow.auth.domain.model.AuthCredentials;
import com.propflow.auth.domain.model.UserRole;
import com.propflow.auth.domain.port.in.*;
import com.propflow.auth.infrastructure.entrypoint.web.dto.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthHandler {
    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;
    private final VerifyEmailUseCase verifyEmailUseCase;
    private final Validator validator;

    public Mono<ServerResponse> register(ServerRequest request) {
        return request.bodyToMono(RegisterRequest.class)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El cuerpo de la petición es obligatorio")))
                .flatMap(body -> validate(body)
                        .then(registerUserUseCase.execute(
                            new RegisterUserUseCase.Command(
                                    body.name(), body.email(),
                                    body.password(), UserRole.valueOf(body.role())
                            )
                        ))
                )
                .flatMap( user -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .bodyValue(new UserResponse(user.id(), user.name(), user.email(), user.role()))
                );
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El cuerpo de la petición es obligatorio")))
                .flatMap(body -> validate(body)
                        .then(loginUseCase.execute(new AuthCredentials(body.email(), body.password())))
                )
                .flatMap(tokens -> ServerResponse.ok()
                        .bodyValue(new TokenResponse(tokens.accessToken(), tokens.refreshToken())));
    }

    public Mono<ServerResponse> refresh(ServerRequest request) {
        return request.bodyToMono(RefreshRequest.class)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El cuerpo de la petición es obligatorio")))
                .flatMap(body -> refreshTokenUseCase.execute(body.refreshToken()))
                .flatMap(tokens -> ServerResponse.ok()
                        .bodyValue(new TokenResponse(tokens.accessToken(), tokens.refreshToken())));
    }

    // ── Logout ────────────────────────────────────────────────────────────────

    public Mono<ServerResponse> logout(ServerRequest request) {
        return request.bodyToMono(RefreshRequest.class)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El cuerpo de la petición es obligatorio")))
                .flatMap(body -> logoutUseCase.execute(body.refreshToken()))
                .then(ServerResponse.noContent().build());
    }

    // ── Verify email ──────────────────────────────────────────────────────────

    public Mono<ServerResponse> verifyEmail(ServerRequest request) {
        return Mono.justOrEmpty(request.queryParam("token"))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El parámetro 'token' es obligatorio")))
                .flatMap(verifyEmailUseCase::execute)
                .then(ServerResponse.ok().bodyValue(MessageResponse.of("Email verificado correctamente")));
    }

    // ── Me ────────────────────────────────────────────────────────────────────

    public Mono<ServerResponse> me(ServerRequest request) {
        return request.principal()
                .flatMap(principal -> ServerResponse.ok().bodyValue(principal.getName()))
                .switchIfEmpty(ServerResponse.status(HttpStatus.UNAUTHORIZED).build());
    }

    private <T> Mono<Void> validate(T body) {
        Set<ConstraintViolation<T>> violations = validator.validate(body);
        if (violations.isEmpty()) return Mono.empty();
        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.joining("; "));
        return Mono.error(new IllegalArgumentException(message));
    }

}
