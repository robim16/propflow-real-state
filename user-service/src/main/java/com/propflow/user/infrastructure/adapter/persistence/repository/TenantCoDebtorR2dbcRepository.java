package com.propflow.user.infrastructure.adapter.persistence.repository;

import com.propflow.user.infrastructure.adapter.persistence.TenantCoDebtorEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TenantCoDebtorR2dbcRepository extends ReactiveCrudRepository<TenantCoDebtorEntity, String> {
    Mono<TenantCoDebtorEntity> findByTenantId(String tenantId);
    Mono<Void> deleteByTenantId(String tenantId);

    @Query("SELECT * FROM tenant_co_debtors WHERE tenant_id IN (:tenantIds)")
    Flux<TenantCoDebtorEntity> findByTenantIdIn(@Param("tenantIds") List<String> tenantIds);
}
