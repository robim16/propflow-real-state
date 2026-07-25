package com.propflow.user.domain.port.in;

import com.propflow.user.domain.model.Landlord;
import com.propflow.user.domain.model.Tenant;
import reactor.core.publisher.Mono;

public interface CreateTenantUseCase {
    Mono<Tenant> createAndPersist(CreateTenantCommand command);
}
