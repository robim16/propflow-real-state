package com.propflow.user.infrastructure.entrypoint.web.shared.validation;

import com.propflow.user.domain.exception.FieldError;
import com.propflow.user.domain.exception.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RequestValidator {

    private final Validator validator;

    public <T> Mono<T> validate(T target) {
        var violations = validator.validate(target);
        if (violations.isEmpty()) {
            return Mono.just(target);
        }
        var errors = violations.stream()
                .map(v -> new FieldError(v.getPropertyPath().toString(), v.getMessage()))
                .toList();
        return Mono.error(new ValidationException(errors));
    }
}
