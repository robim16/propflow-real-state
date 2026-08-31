package com.propflow.user.application.usecase;


import com.propflow.user.application.usecase.validator.TenantDocDuplicatedValidator;
import com.propflow.user.domain.model.Tenant;
import com.propflow.user.domain.port.in.CreateTenantCommand;
import com.propflow.user.domain.port.in.CreateTenantUseCase;
import com.propflow.user.domain.port.out.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateTenantUseCaseImpl implements CreateTenantUseCase {
    private final TenantRepository tenantRepository;
    private final TenantDocDuplicatedValidator tenantDuplicatedValidator;

    @Override
    public Mono<Tenant> createAndPersist(CreateTenantCommand command) {
        return tenantDuplicatedValidator.validateDocumentNotDuplicated(command.documentNumber(), null)
                .then(Mono.defer(() -> buildTenant(command)))
                .flatMap(tenantRepository::save);

    }

    private Mono<Tenant> buildTenant(CreateTenantCommand command) {
        var tenant = Tenant.create(
                command.userId(),
                command.documentType(),
                command.documentNumber(),
                command.references(),
                command.coDebtor()
        );
        return Mono.just(tenant);
    }
}
