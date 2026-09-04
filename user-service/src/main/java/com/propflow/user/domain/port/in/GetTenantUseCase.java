package com.propflow.user.domain.port.in;

import com.propflow.user.domain.model.Tenant;
import com.propflow.user.domain.model.vo.TenantId;
import com.propflow.user.domain.model.vo.UserPrincipal;
import com.propflow.user.domain.port.out.TenantRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GetTenantUseCase {
    Mono<Tenant> getTenant(TenantId tenantId, UserPrincipal userPrincipal);
    Flux<Tenant> getTenants(TenantRepository.TenantQuery query, UserPrincipal userPrincipal);
}
