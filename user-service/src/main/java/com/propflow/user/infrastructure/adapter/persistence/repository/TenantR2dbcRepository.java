package com.propflow.user.infrastructure.adapter.persistence.repository;

import com.propflow.user.infrastructure.adapter.persistence.TenantEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TenantR2dbcRepository extends ReactiveCrudRepository<TenantEntity, String> {
    Mono<TenantEntity> findByDocumentNumber(String documentNumber);


    @Query("""
    SELECT * FROM tenants
    WHERE (:advisorId IS NULL OR advisor_id = :advisorId)
      AND (:status    IS NULL OR status     = :status)
    ORDER BY created_at DESC
    LIMIT :size OFFSET :offset
    """)
    Flux<TenantEntity> findAllByFilters(
            @Param("advisorId") String advisorId,
            @Param("status")    String status,
            @Param("size")      int size,
            @Param("offset")    long offset
    );
}
