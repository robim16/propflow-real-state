package com.propflow.user.application.usecase;

import com.propflow.user.application.usecase.validator.TenantDocDuplicatedValidator;
import com.propflow.user.domain.exception.TenantNotFoundException;
import com.propflow.user.domain.model.Tenant;
import com.propflow.user.domain.port.in.UpdateTenantCommand;
import com.propflow.user.domain.port.in.UpdateTenantUseCase;
import com.propflow.user.domain.port.out.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateTenantUseCaseImpl implements UpdateTenantUseCase {
    private final TenantRepository tenantRepository;
    private final TenantDocDuplicatedValidator  tenantDocDuplicatedValidator;

    @Override
    public Mono<Tenant> updateTenant(UpdateTenantCommand command) {
        return tenantRepository.findById(command.tenantId())
                .switchIfEmpty(Mono.error(new TenantNotFoundException(command.tenantId())))
                .flatMap(tenant -> validateUpdates(tenant, command))
                .map(tenant -> applyUpdates(tenant, command))
                .flatMap(tenantRepository::save);
    }

    private Mono<Tenant> validateUpdates(Tenant tenant, UpdateTenantCommand command) {
        Mono<Void> docValidation = command.documentNumber() != null
                ? tenantDocDuplicatedValidator.validateDocumentNotDuplicated(
                        command.documentNumber(), command.tenantId()) : Mono.empty();
        return docValidation
                .thenReturn(tenant);
    }

    private Tenant applyUpdates(Tenant tenant, UpdateTenantCommand command) {
        tenant.updateProfile(
                command.documentType(),
                command.documentNumber(),
                command.references(),
                command.coDebtor()
        );
        return tenant;
    }
}
