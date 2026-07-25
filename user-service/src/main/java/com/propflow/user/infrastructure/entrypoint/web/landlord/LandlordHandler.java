package com.propflow.user.infrastructure.entrypoint.web.landlord;

import com.propflow.user.domain.model.vo.LandlordId;
import com.propflow.user.domain.port.in.CreateLandlordUseCase;
import com.propflow.user.domain.port.in.GetLandlordUseCase;
import com.propflow.user.domain.port.in.UpdateLandlordUseCase;
import com.propflow.user.domain.port.out.LandlordRepository;
import com.propflow.user.infrastructure.entrypoint.web.landlord.request.CreateLandlordRequest;
import com.propflow.user.infrastructure.entrypoint.web.landlord.request.UpdateLandlordRequest;
import com.propflow.user.infrastructure.entrypoint.web.landlord.response.LandlordResponse;
import com.propflow.user.infrastructure.entrypoint.web.shared.ErrorHandlingSupport;
import com.propflow.user.infrastructure.entrypoint.web.shared.PrincipalExtractor;
import com.propflow.user.infrastructure.entrypoint.web.shared.validation.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;


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


    public Mono<ServerResponse> list(ServerRequest request) {
        var query = LandlordRepository.LandlordQuery.builder()
                .advisorId(request.queryParam("advisorId").orElse(null))
                .status(request.queryParam("status").orElse(null))
                .page(Integer.parseInt(request.queryParam("page").orElse("0")))
                .size(Integer.parseInt(request.queryParam("size").orElse("20")))
                .build();

        return withErrorHandling(
                principalExtractor.extract(request)
                        .flatMapMany(principal -> getLandlordUseCase.list(query, principal))
                        .map(LandlordResponse::from)
                        .collectList()
                        .flatMap(landlords -> ServerResponse.ok().bodyValue(landlords))
        );
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        var landlordId = LandlordId.of(request.pathVariable("id"));
        return withErrorHandling(
                principalExtractor.extract(request)
                        .flatMap(principal -> getLandlordUseCase.getLandlord(landlordId, principal))
                        .flatMap(landlord -> ServerResponse.ok().bodyValue(LandlordResponse.from(landlord)))
                        .flatMap(validator::validate)
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
