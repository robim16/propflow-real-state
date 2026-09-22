package com.propflow.user.application.usecase;

import com.propflow.user.domain.model.Tenant;
import com.propflow.user.domain.port.in.UpdateTenantCommand;
import com.propflow.user.domain.port.in.UpdateTenantUseCase;
import reactor.core.publisher.Mono;

public class UpdateTenantUseCaseImpl implements UpdateTenantUseCase {
    @Override
    public Mono<Tenant> updateTenant(UpdateTenantCommand command) {

        return null;
    }
}
