package com.propflow.user.application.usecase;


import com.propflow.user.domain.model.Tenant;
import com.propflow.user.domain.port.in.CreateTenantCommand;
import com.propflow.user.domain.port.in.CreateTenantUseCase;
import reactor.core.publisher.Mono;

public class CreateTenantUseCaseImpl implements CreateTenantUseCase {

    @Override
    public Mono<Tenant> createAndPersist(CreateTenantCommand command) {
        return null;
    }
}
