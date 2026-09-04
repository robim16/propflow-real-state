package com.propflow.user.infrastructure.adapter.persistence;

import com.propflow.user.domain.model.Tenant;
import com.propflow.user.domain.model.vo.TenantId;
import com.propflow.user.domain.port.out.TenantRepository;
import com.propflow.user.infrastructure.adapter.persistence.mapper.TenantEntityMapper;
import com.propflow.user.infrastructure.adapter.persistence.repository.TenantCoDebtorR2dbcRepository;
import com.propflow.user.infrastructure.adapter.persistence.repository.TenantR2dbcRepository;
import com.propflow.user.infrastructure.adapter.persistence.repository.TenantReferenceR2dbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TenantRepositoryAdapter implements TenantRepository {

    private final TenantR2dbcRepository r2dbcRepository;
    private final TenantReferenceR2dbcRepository tenantReferenceR2dbcRepository;
    private final TenantCoDebtorR2dbcRepository tenantCoDebtorR2dbcRepository;
    private final TenantEntityMapper mapper;

    @Override
    public Mono<Tenant> save(Tenant tenant) {
        return r2dbcRepository.save(mapper.toEntity(tenant))
                .flatMap(savedEntity -> saveReferences(tenant)
                        .then(saveCoDebtor(tenant))
                        .thenReturn(savedEntity))
                .map(savedEntity -> mapper.toDomain(
                        (TenantEntity) savedEntity,
                        mapper.toReferenceEntities(tenant),
                        mapper.toCoDebtorEntity(tenant)
                ));
    }

    @Override
    public Mono<Tenant> findById(TenantId tenantId) {
        return r2dbcRepository.findById(tenantId.value())
                .flatMap(entity -> Mono.zip(
                        tenantReferenceR2dbcRepository.findByTenantId(entity.getId()).collectList(),
                        tenantCoDebtorR2dbcRepository.findByTenantId(entity.getId())
                                .map(Optional::of)
                                .defaultIfEmpty(Optional.empty()),
                        (references, coDebtor) ->
                                mapper.toDomain(entity, references, coDebtor.orElse(null))
                ));
    }

    @Override
    public Flux<Tenant> findAll(TenantQuery query) {
        return null;
    }

    @Override
    public Mono<Tenant> findByDocumentNumber(String documentNumber) {
        return r2dbcRepository.findByDocumentNumber(documentNumber)
                .flatMap(entity -> Mono.zip(
                        tenantReferenceR2dbcRepository.findByTenantId(entity.getId()).collectList(),
                        tenantCoDebtorR2dbcRepository.findByTenantId(entity.getId())
                                .map(Optional::of)
                                .defaultIfEmpty(Optional.empty()),
                        (references, coDebtor) ->
                                mapper.toDomain(entity, references, coDebtor.orElse(null))
                ));
    }

    private Mono<Void> saveReferences(Tenant tenant) {
        List<TenantReferenceEntity> referenceEntities =
                mapper.toReferenceEntities(tenant);

        if (referenceEntities.isEmpty()) {
            return Mono.empty();
        }

        return tenantReferenceR2dbcRepository.deleteByTenantId(tenant.getId().value())
                .thenMany(tenantReferenceR2dbcRepository.saveAll(referenceEntities))
                .then();
    }

    private Mono<Void> saveCoDebtor(Tenant tenant) {
        TenantCoDebtorEntity coDebtorEntity =
                mapper.toCoDebtorEntity(tenant);

        return tenantCoDebtorR2dbcRepository.deleteByTenantId(tenant.getId().value())
                .then(
                        (coDebtorEntity != null
                                                        ? tenantCoDebtorR2dbcRepository.save(coDebtorEntity).then()
                                                        : Mono.empty()).then()
                );
    }
}



