package com.propflow.user.infrastructure.entrypoint.web.tenant;

import com.propflow.user.domain.port.in.CreateTenantUseCase;
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
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class TenantHandler implements ErrorHandlingSupport {

    private final PrincipalExtractor principalExtractor;
    private final RequestValidator validator;
    private final CreateTenantUseCase createTenantUseCase;

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
}
