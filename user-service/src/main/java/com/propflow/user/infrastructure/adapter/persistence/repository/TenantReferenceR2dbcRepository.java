package com.propflow.user.infrastructure.adapter.persistence.repository;

import com.propflow.user.infrastructure.adapter.persistence.TenantReferenceEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TenantReferenceR2dbcRepository extends ReactiveCrudRepository<TenantReferenceEntity, String> {
    Mono<Void> deleteByTenantId(String tenantId);
}
