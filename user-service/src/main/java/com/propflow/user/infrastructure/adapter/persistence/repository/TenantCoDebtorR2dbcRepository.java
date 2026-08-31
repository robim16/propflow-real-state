package com.propflow.user.infrastructure.adapter.persistence.repository;

import com.propflow.user.infrastructure.adapter.persistence.TenantCoDebtorEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TenantCoDebtorR2dbcRepository extends ReactiveCrudRepository<TenantCoDebtorEntity, String> {
    Mono<TenantCoDebtorEntity> findByTenantId(String tenantId);
    Mono<Void> deleteByTenantId(String tenantId);
}
