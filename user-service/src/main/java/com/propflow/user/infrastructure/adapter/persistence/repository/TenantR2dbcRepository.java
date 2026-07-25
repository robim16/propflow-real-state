package com.propflow.user.infrastructure.adapter.persistence.repository;

import com.propflow.user.infrastructure.adapter.persistence.TenantEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TenantR2dbcRepository extends ReactiveCrudRepository<TenantEntity, String> {
}
