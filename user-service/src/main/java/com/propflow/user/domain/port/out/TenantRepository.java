package com.propflow.user.domain.port.out;


import com.propflow.user.domain.model.Tenant;
import reactor.core.publisher.Mono;

public interface TenantRepository {
    Mono<Tenant> save(Tenant tenant);
    Mono<Tenant> findByDocumentNumber(String documentNumber);
}
