package com.propflow.user.infrastructure.entrypoint.web;

import com.propflow.user.domain.exception.*;
import com.propflow.user.domain.model.vo.LandlordId;
import com.propflow.user.domain.model.vo.Role;
import com.propflow.user.domain.model.vo.UserId;
import com.propflow.user.domain.model.vo.UserPrincipal;
import com.propflow.user.domain.port.in.CreateLandlordCommand;
import com.propflow.user.domain.port.in.CreateLandlordUseCase;
import com.propflow.user.domain.port.in.GetLandlordUseCase;
import com.propflow.user.infrastructure.entrypoint.web.dto.CreateLandlordRequest;
import com.propflow.user.infrastructure.entrypoint.web.response.ErrorResponse;
import com.propflow.user.infrastructure.entrypoint.web.response.LandlordResponse;
import com.propflow.user.infrastructure.entrypoint.web.shared.validation.RequestValidator;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final CreateLandlordUseCase    createLandlordUseCase;
    private final GetLandlordUseCase       getLandlordUseCase;
    //private final UpdateLandlordUseCase    updateLandlordUseCase;
    //private final CreateTenantUseCase      createTenantUseCase;
    //private final GetTenantUseCase         getTenantUseCase;
    //private final CreateAssignmentUseCase  createAssignmentUseCase;
    //private final RequestUploadUrlUseCase  requestUploadUrlUseCase;
    //private final VerifyDocumentUseCase    verifyDocumentUseCase;
    private final RequestValidator validator;

    public Mono<ServerResponse> createLandlord(ServerRequest request) {
        return extractUserId(request)
                .flatMap(userId -> request.bodyToMono(CreateLandlordRequest.class)
                        .flatMap(validator::validate)
                        .flatMap(body -> createLandlordUseCase.createAndPersist(
                                new CreateLandlordCommand(
                                        userId,
                                        body.documentType().toDomain(),
                                        body.documentNumber().trim(),
                                        body.address().trim(),
                                        body.bankAccount().bank().trim(),
                                        body.bankAccount().accountType().toDomain(),
                                        body.bankAccount().accountNumber()
                                )
                        ))
                )
                .flatMap(landlord -> ServerResponse
                        .created(URI.create("/api/v1/landlords/" + landlord.getId().value()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(LandlordResponse.from(landlord)))
                .onErrorResume(ValidationException.class, e ->
                        ServerResponse.badRequest()
                                .bodyValue(ErrorResponse.ofValidation(e.getErrors())))
                .onErrorResume(InvalidRequestException.class, e ->
                        ServerResponse.badRequest()
                                .bodyValue(ErrorResponse.of("INVALID_FORMAT", e.getMessage())))
                .onErrorResume(ProfileAlreadyExistsException.class, e ->
                        ServerResponse.status(HttpStatus.CONFLICT)
                                .bodyValue(ErrorResponse.of("PROFILE_EXISTS", e.getMessage())))
                .onErrorResume(DuplicateDocumentException.class, e ->
                        ServerResponse.status(HttpStatus.CONFLICT)
                                .bodyValue(ErrorResponse.of("DUPLICATE_DOCUMENT", e.getMessage())));
    }

    public Mono<ServerResponse> getLandlord(ServerRequest request) {
        LandlordId landlordId = LandlordId.of(request.pathVariable("id"));

        return extractPrincipal(request)
                .flatMap(userPrincipal -> getLandlordUseCase.execute(landlordId, userPrincipal))
                .flatMap(landlord -> ServerResponse.ok().bodyValue(LandlordResponse.from(landlord)))
                .onErrorResume(LandlordNotFoundException.class, e ->
                        ServerResponse.status(HttpStatus.NOT_FOUND)
                                .bodyValue(ErrorResponse.of("LANDLORD_NOT_FOUND", e.getMessage())))
                .onErrorResume(AccessDeniedException.class, e ->
                        ServerResponse.status(HttpStatus.FORBIDDEN)
                                .bodyValue(ErrorResponse.of("ACCESS_DENIED", e.getMessage())));
    }

    private Mono<UserId> extractUserId(ServerRequest request) {
        return request.principal()
                .map(p -> UserId.of(((JwtAuthenticationToken) p).getToken()
                        .getClaimAsString("userId")));
    }

    private Mono<UserPrincipal> extractPrincipal(ServerRequest request) {
        return request.principal()
                .cast(JwtAuthenticationToken.class)
                .map(token -> {
                    var claims = token.getToken().getClaims();
                    return new UserPrincipal(
                            UserId.of((String) claims.get("userId")),
                            Role.valueOf((String) claims.get("role")),
                            (String) claims.get("advisorId"),
                            (String) claims.get("landlordId"),
                            (String) claims.get("tenantId")
                    );
                });
    }
}
