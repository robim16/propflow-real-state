package com.propflow.user.infrastructure.adapter.persistence.repository;

import com.propflow.user.infrastructure.adapter.persistence.LandlordEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LandlordR2dbcRepository extends ReactiveCrudRepository<LandlordEntity, String> {
    Mono<LandlordEntity> findByAccountNumberHash(String accountNumberHash);
    Mono<LandlordEntity> findByDocumentNumber(String documentNumber);
    Mono<LandlordEntity> findByUserId(String userId);

    @Query("""
    SELECT * FROM landlords
    WHERE (:advisorId IS NULL OR advisor_id = :advisorId)
      AND (:status    IS NULL OR status     = :status)
    ORDER BY created_at DESC
    LIMIT :size OFFSET :offset
    """)
    Flux<LandlordEntity> findAllByFilters(
            @Param("advisorId") String advisorId,
            @Param("status")    String status,
            @Param("size")      int size,
            @Param("offset")    long offset
    );
}
