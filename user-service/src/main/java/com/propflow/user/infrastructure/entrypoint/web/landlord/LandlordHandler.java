package com.propflow.user.infrastructure.entrypoint.web.landlord;

import com.propflow.user.domain.exception.LandlordNotFoundException;
import com.propflow.user.domain.model.vo.LandlordId;
import com.propflow.user.domain.port.in.CreateLandlordUseCase;
import com.propflow.user.domain.port.in.GetLandlordUseCase;
import com.propflow.user.domain.port.in.UpdateLandlordUseCase;
import com.propflow.user.infrastructure.entrypoint.web.landlord.request.CreateLandlordRequest;
import com.propflow.user.infrastructure.entrypoint.web.landlord.request.UpdateLandlordRequest;
import com.propflow.user.infrastructure.entrypoint.web.landlord.response.LandlordResponse;
import com.propflow.user.infrastructure.entrypoint.web.response.ErrorResponse;
import com.propflow.user.infrastructure.entrypoint.web.shared.ErrorHandlingSupport;
import com.propflow.user.infrastructure.entrypoint.web.shared.PrincipalExtractor;
import com.propflow.user.infrastructure.entrypoint.web.shared.validation.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.file.AccessDeniedException;

@Component
@RequiredArgsConstructor
public class LandlordHandler implements ErrorHandlingSupport {

    private final CreateLandlordUseCase createLandlordUseCase;
    private final GetLandlordUseCase getLandlordUseCase;
    private final UpdateLandlordUseCase updateLandlordUseCase;
    private final PrincipalExtractor principalExtractor;
    private final RequestValidator validator;

    public Mono<ServerResponse> create(ServerRequest request) {
        return withErrorHandling(
                principalExtractor.extractUserId(request)
                        .flatMap(userId -> request.bodyToMono(CreateLandlordRequest.class)
                                .flatMap(validator::validate)
                                .flatMap(body -> createLandlordUseCase.createAndPersist(body.toCommand(userId))))
                        .flatMap(landlord -> ServerResponse
                                .created(URI.create("/api/v1/landlords/" + landlord.getId().value()))
                                .bodyValue(LandlordResponse.from(landlord)))
        );
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        var landlordId = LandlordId.of(request.pathVariable("id"));
        return withErrorHandling(
                principalExtractor.extract(request)
                        .flatMap(principal -> getLandlordUseCase.execute(landlordId, principal))
                        .flatMap(landlord -> ServerResponse.ok().bodyValue(LandlordResponse.from(landlord)))
                        .flatMap(validator::validate)
        );
    }

    // user-service/src/main/java/com/propflow/userservice/infrastructure/entrypoint/web/landlord/LandlordHandler.java

package com.propflow.userservice.infrastructure.entrypoint.web.landlord;

import com.propflow.userservice.domain.model.vo.LandlordId;
import com.propflow.userservice.domain.port.in.*;
import com.propflow.userservice.infrastructure.entrypoint.web.landlord.request.CreateLandlordRequest;
import com.propflow.userservice.infrastructure.entrypoint.web.landlord.request.UpdateLandlordRequest;
import com.propflow.userservice.infrastructure.entrypoint.web.landlord.response.LandlordResponse;
import com.propflow.userservice.infrastructure.entrypoint.web.shared.ErrorHandlingSupport;
import com.propflow.userservice.infrastructure.entrypoint.web.shared.PrincipalExtractor;
import com.propflow.userservice.infrastructure.entrypoint.web.shared.validation.RequestValidator;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import java.net.URI;

    @Component
    public class LandlordHandler implements ErrorHandlingSupport {

        private final CreateLandlordUseCase createLandlordUseCase;
        private final GetLandlordUseCase    getLandlordUseCase;
        private final UpdateLandlordUseCase updateLandlordUseCase;
        private final PrincipalExtractor    principalExtractor;
        private final RequestValidator      validator;

        public LandlordHandler(
                CreateLandlordUseCase createLandlordUseCase,
                GetLandlordUseCase getLandlordUseCase,
                UpdateLandlordUseCase updateLandlordUseCase,
                PrincipalExtractor principalExtractor,
                RequestValidator validator) {
            this.createLandlordUseCase = createLandlordUseCase;
            this.getLandlordUseCase    = getLandlordUseCase;
            this.updateLandlordUseCase = updateLandlordUseCase;
            this.principalExtractor    = principalExtractor;
            this.validator             = validator;
        }

        public Mono<ServerResponse> create(ServerRequest request) {
            return withErrorHandling(
                    principalExtractor.extractUserId(request)
                            .flatMap(userId -> request.bodyToMono(CreateLandlordRequest.class)
                                    .flatMap(validator::validate)
                                    .flatMap(body -> createLandlordUseCase.execute(body.toCommand(userId))))
                            .flatMap(landlord -> ServerResponse
                                    .created(URI.create("/api/v1/landlords/" + landlord.getId().value()))
                                    .bodyValue(LandlordResponse.from(landlord)))
            );
        }

        public Mono<ServerResponse> getById(ServerRequest request) {
            var landlordId = LandlordId.of(request.pathVariable("id"));
            return withErrorHandling(
                    principalExtractor.extract(request)
                            .flatMap(principal -> getLandlordUseCase.execute(landlordId, principal))
                            .flatMap(landlord -> ServerResponse.ok().bodyValue(LandlordResponse.from(landlord)))
            );
        }

        public Mono<ServerResponse> update(ServerRequest request) {
            var landlordId = LandlordId.of(request.pathVariable("id"));
            return withErrorHandling(
                    principalExtractor.extract(request)
                            .flatMap(principal -> request.bodyToMono(UpdateLandlordRequest.class)
                                    .flatMap(validator::validate)
                                    .flatMap(body -> updateLandlordUseCase.update(
                                            body.toCommand(landlordId, principal))))
                            .flatMap(landlord -> ServerResponse.ok().bodyValue(LandlordResponse.from(landlord)))
            );
        }
    }
}
