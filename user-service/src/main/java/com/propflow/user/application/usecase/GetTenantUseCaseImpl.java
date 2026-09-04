package com.propflow.user.application.usecase;

import com.propflow.user.domain.model.Tenant;
import com.propflow.user.domain.model.vo.TenantId;
import com.propflow.user.domain.model.vo.UserPrincipal;
import com.propflow.user.domain.port.in.GetTenantUseCase;
import com.propflow.user.domain.port.out.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@RequiredArgsConstructor
public class GetTenantUseCaseImpl implements GetTenantUseCase {
    private final TenantRepository tenantRepository;

    @Override
    public Mono<Tenant> getTenant(TenantId tenantId, UserPrincipal userPrincipal) {
        return tenantRepository.findById(tenantId);
    }

    @Override
    public Flux<Tenant> getTenants(TenantRepository.TenantQuery query, UserPrincipal userPrincipal) {
        return null;
    }
}
