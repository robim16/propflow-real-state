package com.propflow.user.infrastructure.adapter.persistence;

import com.propflow.user.domain.model.Landlord;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface LandlordR2dbcRepository extends ReactiveCrudRepository<LandlordEntity, String> {
    Mono<LandlordEntity> findByAccountNumberHash(String accountNumberHash);
    Mono<Boolean> existsByDocumentNumber(String documentNumber);
}
