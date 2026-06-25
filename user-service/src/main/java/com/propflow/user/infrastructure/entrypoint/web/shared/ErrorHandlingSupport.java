package com.propflow.user.infrastructure.entrypoint.web.shared;

import com.propflow.user.domain.exception.*;
import com.propflow.user.infrastructure.entrypoint.web.shared.response.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public interface ErrorHandlingSupport {

    default Mono<ServerResponse> withErrorHandling(Mono<ServerResponse> response) {
        return response
                .onErrorResume(ProfileAlreadyExistsException.class, e ->
                        ServerResponse.status(HttpStatus.CONFLICT)
                                .bodyValue(ErrorResponse.of("PROFILE_EXISTS", e.getMessage())))
                .onErrorResume(DuplicateDocumentException.class, e ->
                        ServerResponse.status(HttpStatus.CONFLICT)
                                .bodyValue(ErrorResponse.of("DUPLICATE_DOCUMENT", e.getMessage())))
                .onErrorResume(LandlordNotFoundException.class, e ->
                        ServerResponse.status(HttpStatus.NOT_FOUND)
                                .bodyValue(ErrorResponse.of("LANDLORD_NOT_FOUND", e.getMessage())))
                .onErrorResume(TenantNotFoundException.class, e ->
                        ServerResponse.status(HttpStatus.NOT_FOUND)
                                .bodyValue(ErrorResponse.of("TENANT_NOT_FOUND", e.getMessage())))
                .onErrorResume(DocumentNotFoundException.class, e ->
                        ServerResponse.status(HttpStatus.NOT_FOUND)
                                .bodyValue(ErrorResponse.of("DOCUMENT_NOT_FOUND", e.getMessage())))
                .onErrorResume(AdvisorNotFoundException.class, e ->
                        ServerResponse.status(HttpStatus.NOT_FOUND)
                                .bodyValue(ErrorResponse.of("ADVISOR_NOT_FOUND", e.getMessage())))
                .onErrorResume(AssignmentNotFoundException.class, e ->
                        ServerResponse.status(HttpStatus.NOT_FOUND)
                                .bodyValue(ErrorResponse.of("ASSIGNMENT_NOT_FOUND", e.getMessage())))
                .onErrorResume(AccessDeniedException.class, e ->
                        ServerResponse.status(HttpStatus.FORBIDDEN)
                                .bodyValue(ErrorResponse.of("ACCESS_DENIED", e.getMessage())))
                .onErrorResume(UnauthorizedRoleException.class, e ->
                        ServerResponse.status(HttpStatus.FORBIDDEN)
                                .bodyValue(ErrorResponse.of("UNAUTHORIZED_ROLE", e.getMessage())))
                .onErrorResume(AdvisorCapacityExceededException.class, e ->
                        ServerResponse.status(HttpStatus.UNPROCESSABLE_ENTITY)
                                .bodyValue(ErrorResponse.of("CAPACITY_EXCEEDED", e.getMessage())))
                .onErrorResume(FileSizeExceededException.class, e ->
                        ServerResponse.status(HttpStatus.PAYLOAD_TOO_LARGE)
                                .bodyValue(ErrorResponse.of("FILE_TOO_LARGE", e.getMessage())))
                .onErrorResume(InvalidFileTypeException.class, e ->
                        ServerResponse.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                                .bodyValue(ErrorResponse.of("INVALID_TYPE", e.getMessage())))
                .onErrorResume(ValidationException.class, e ->
                        ServerResponse.status(HttpStatus.UNPROCESSABLE_ENTITY)
                                .bodyValue(ErrorResponse.ofValidation(e.getErrors())))
                .onErrorResume(IllegalArgumentException.class, e ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST)
                                .bodyValue(ErrorResponse.of("INVALID_INPUT", e.getMessage())));
    }
}