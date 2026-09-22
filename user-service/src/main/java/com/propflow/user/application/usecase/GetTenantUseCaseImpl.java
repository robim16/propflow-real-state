package com.propflow.user.application.usecase;

import com.propflow.user.domain.exception.TenantNotFoundException;
import com.propflow.user.domain.model.Tenant;
import com.propflow.user.domain.model.vo.TenantId;
import com.propflow.user.domain.model.vo.UserPrincipal;
import com.propflow.user.domain.port.in.GetTenantUseCase;
import com.propflow.user.domain.port.out.TenantRepository;
import com.propflow.user.domain.port.out.TenantRepository.TenantQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;


@Slf4j
@Service
@RequiredArgsConstructor
public class GetTenantUseCaseImpl implements GetTenantUseCase {
    private final TenantRepository tenantRepository;

    @Override
    public Mono<Tenant> getTenant(TenantId tenantId, UserPrincipal userPrincipal) {
        return tenantRepository.findById(tenantId)
                .switchIfEmpty(Mono.error(new TenantNotFoundException(tenantId)))
                .flatMap(tenant -> validateAccess(tenant, userPrincipal));
    }

    @Override
    public Flux<Tenant> getTenants(TenantQuery query, UserPrincipal userPrincipal) {
        return validateListAccess(userPrincipal)
                .thenMany(tenantRepository.findAll(buildQuery(query, userPrincipal)));
    }

    private Mono<Tenant> validateAccess(Tenant tenant, UserPrincipal principal) {
        if (principal.isAdmin()) {
            return Mono.just(tenant);
        }
        if (principal.isAdvisor()
                && tenant.getAdvisorId() != null
                && tenant.getAdvisorId().equals(principal.advisorId())) {
            return Mono.just(tenant);
        }
        if (principal.ownsTenantProfile(tenant.getId().value())) {
            return Mono.just(tenant);
        }
        return Mono.error(new AccessDeniedException(
                "No tienes permisos para ver este perfil"));
    }

    private Mono<Void> validateListAccess(UserPrincipal principal) {
        if (principal.isAdmin() || principal.isAdvisor()) {
            return Mono.empty();
        }
        return Mono.error(new AccessDeniedException(
                "No tienes permisos para listar arrendatarios"));
    }

    private TenantQuery buildQuery(TenantRepository.TenantQuery query, UserPrincipal principal) {
        if (principal.isAdmin()) {
            return query;
        }
        return TenantQuery.builder()
                .advisorId(principal.advisorId())
                .status(query.status())
                .page(query.page())
                .size(query.size())
                .build();
    }

}
