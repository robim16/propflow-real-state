package com.propflow.user.application.usecase.validator;

import com.propflow.user.domain.exception.DuplicateDocumentException;
import com.propflow.user.domain.model.vo.TenantId;
import com.propflow.user.domain.port.out.TenantRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class TenantDocDuplicatedValidator {
    private final TenantRepository tenantRepository;

    /**
     * Valida que el número de documento no esté duplicado.
     *
     * CREATE (existingTenantId = null):
     *   - Si el documento existe en otro tenant → rechazar siempre.
     *   - Si no existe → permitir.
     *
     * UPDATE (existingTenantId = id del tenant que está actualizando):
     *   - Si el documento existe y pertenece al mismo tenant → permitir (no cambió o es suyo).
     *   - Si el documento existe y pertenece a otro tenant → rechazar.
     *   - Si no existe → permitir (está asignando un documento diferente).
     */
    public Mono<Void> validateDocumentNotDuplicated(
            String documentNumber,
            TenantId existingTenantId) {

        return tenantRepository.findByDocumentNumber(documentNumber)
                .flatMap(ownerTenant -> {
                    // El documento existe — verificar a quién pertenece
                    boolean ownedBySameTenant = existingTenantId != null
                            && ownerTenant.getId().value().equals(existingTenantId.value());

                    if (ownedBySameTenant) {
                        // UPDATE: el documento ya era de este landlord → permitir
                        return Mono.<Void>empty();
                    }

                    // CREATE: no hay landlordId aún, o el documento pertenece a otro → rechazar
                    return Mono.<Void>error(new DuplicateDocumentException(documentNumber));
                })
                // El documento no existe en ningún landlord → permitir siempre
                .switchIfEmpty(Mono.empty());
    }

}
