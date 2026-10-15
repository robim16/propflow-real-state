package com.propflow.user.infrastructure.adapter.persistence.repository;

import com.propflow.user.infrastructure.adapter.persistence.AdvisorEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AdvisorR2dbcRepository extends ReactiveCrudRepository<AdvisorEntity, String> {
}
