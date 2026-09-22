package com.propflow.user.domain.port.in;

import com.propflow.user.domain.model.Tenant;
import reactor.core.publisher.Mono;

public interface UpdateTenantUseCase {
    Mono<Tenant> updateTenant(UpdateTenantCommand command);
}
