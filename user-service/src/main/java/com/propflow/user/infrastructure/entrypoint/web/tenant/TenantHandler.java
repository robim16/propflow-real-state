package com.propflow.user.infrastructure.entrypoint.web.tenant;

import com.propflow.user.domain.model.vo.TenantId;
import com.propflow.user.domain.port.in.CreateTenantUseCase;
import com.propflow.user.domain.port.in.GetTenantUseCase;
import com.propflow.user.domain.port.out.TenantRepository;
import com.propflow.user.infrastructure.entrypoint.web.landlord.response.LandlordResponse;
import com.propflow.user.infrastructure.entrypoint.web.shared.ErrorHandlingSupport;
import com.propflow.user.infrastructure.entrypoint.web.shared.PrincipalExtractor;
import com.propflow.user.infrastructure.entrypoint.web.shared.validation.RequestValidator;
import com.propflow.user.infrastructure.entrypoint.web.tenant.request.CreateTenantRequest;
import com.propflow.user.infrastructure.entrypoint.web.tenant.response.TenantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class TenantHandler implements ErrorHandlingSupport {

    private final PrincipalExtractor principalExtractor;
    private final RequestValidator validator;
    private final CreateTenantUseCase createTenantUseCase;
    private final GetTenantUseCase getTenantUseCase;

    public Mono<ServerResponse> create(ServerRequest request) {
        return withErrorHandling(
                principalExtractor.extractUserId(request)
                        .flatMap(userId -> request.bodyToMono(CreateTenantRequest.class)
                                .flatMap(validator::validate)
                                .flatMap(body -> createTenantUseCase.createAndPersist(body.toCommand(userId))))
                        .flatMap(tenant -> ServerResponse
                                .created(URI.create("/api/v1/tenants/" + tenant.getId().value()))
                                .bodyValue(TenantResponse.from(tenant)))
        );

    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        var tenantId = TenantId.of(request.pathVariable("id"));
        return withErrorHandling(
                principalExtractor.extract(request)
                        .flatMap(principal -> getTenantUseCase.getTenant(tenantId, principal))
                        .flatMap(tenant -> ServerResponse.ok().bodyValue(TenantResponse.from(tenant)))
        );
    }

    public Mono<ServerResponse> getTenants(ServerRequest request) {
        var query = TenantRepository.TenantQuery.builder()
                .advisorId(request.queryParam("advisorId").orElse(null))
                .status(request.queryParam("status").orElse(null))
                .page(Integer.parseInt(request.queryParam("page").orElse("0")))
                .size(Integer.parseInt(request.queryParam("size").orElse("10")))
                .build();

        return withErrorHandling(
                principalExtractor.extract(request)
                        .flatMapMany(principal -> getTenantUseCase.getTenants(query, principal))
                        .map(TenantResponse::from)
                        .collectList()
                        .flatMap(tenants -> ServerResponse.ok().bodyValue(tenants))
        );

    }
}
