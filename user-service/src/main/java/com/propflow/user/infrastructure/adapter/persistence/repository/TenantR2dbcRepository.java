package com.propflow.user.infrastructure.adapter.persistence.repository;

import com.propflow.user.infrastructure.adapter.persistence.TenantEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TenantR2dbcRepository extends ReactiveCrudRepository<TenantEntity, String> {
    Mono<TenantEntity> findByDocumentNumber(String documentNumber);
}
