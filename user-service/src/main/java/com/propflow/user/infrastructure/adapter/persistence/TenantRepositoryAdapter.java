package com.propflow.user.infrastructure.adapter.persistence;

import com.propflow.user.domain.model.Tenant;
import com.propflow.user.domain.port.out.TenantRepository;
import com.propflow.user.infrastructure.adapter.persistence.repository.TenantR2dbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TenantRepositoryAdapter implements TenantRepository {

    private final TenantR2dbcRepository r2dbcRepository;

    @Override
    public Mono<Tenant> save(Tenant tenant) {
        return r2dbcRepository.save(tenant)
    }
}
