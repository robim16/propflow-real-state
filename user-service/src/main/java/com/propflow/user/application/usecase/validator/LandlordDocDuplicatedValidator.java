package com.propflow.user.application.usecase.validator;

import com.propflow.user.domain.exception.DuplicateBankAccountException;
import com.propflow.user.domain.exception.DuplicateDocumentException;
import com.propflow.user.domain.model.vo.LandlordId;
import com.propflow.user.domain.port.out.CryptoPort;
import com.propflow.user.domain.port.out.LandlordRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LandlordDocDuplicatedValidator {

    private final LandlordRepository landlordRepository;
    private final CryptoPort cryptoPort;

    /**
     * Valida que el número de documento no esté duplicado.
     *
     * CREATE (existingLandlordId = null):
     *   - Si el documento existe en otro landlord → rechazar siempre.
     *   - Si no existe → permitir.
     *
     * UPDATE (existingLandlordId = id del landlord que está actualizando):
     *   - Si el documento existe y pertenece al mismo landlord → permitir (no cambió o es suyo).
     *   - Si el documento existe y pertenece a otro landlord → rechazar.
     *   - Si no existe → permitir (está asignando un documento diferente).
     */
    public Mono<Void> validateDocumentNotDuplicated(
            String documentNumber,
            LandlordId existingLandlordId) {

        return landlordRepository.findByDocumentNumber(documentNumber)
                .flatMap(ownerLandlord -> {
                    // El documento existe — verificar a quién pertenece
                    boolean ownedBySameLandlord = existingLandlordId != null
                            && ownerLandlord.getId().value().equals(existingLandlordId.value());

                    if (ownedBySameLandlord) {
                        // UPDATE: el documento ya era de este landlord → permitir
                        return Mono.<Void>empty();
                    }

                    // CREATE: no hay landlordId aún, o el documento pertenece a otro → rechazar
                    return Mono.<Void>error(new DuplicateDocumentException(documentNumber));
                })
                // El documento no existe en ningún landlord → permitir siempre
                .switchIfEmpty(Mono.empty());
    }

    /**
     * Valida que el número de cuenta bancaria no esté duplicado.
     *
     * CREATE (existingLandlordId = null):
     *   - Si el hash de cuenta existe en otro landlord → rechazar siempre.
     *   - Si no existe → permitir.
     *
     * UPDATE (existingLandlordId = id del landlord que está actualizando):
     *   - Si el hash existe y pertenece al mismo landlord → permitir (no cambió o es suya).
     *   - Si el hash existe y pertenece a otro landlord → rechazar.
     *   - Si no existe → permitir (está asignando una cuenta diferente).
     */
    public Mono<Void> validateAccountNotDuplicated(
            String rawAccountNumber,
            LandlordId existingLandlordId) {

        var accountNumberHash = cryptoPort.hash(rawAccountNumber);

        return landlordRepository.findByHashedAccountNumber(accountNumberHash)
                .flatMap(ownerLandlord -> {
                    boolean ownedBySameLandlord = existingLandlordId != null
                            && ownerLandlord.getId().value().equals(existingLandlordId.value());

                    if (ownedBySameLandlord) {
                        // UPDATE: la cuenta ya era de este landlord → permitir
                        return Mono.<Void>empty();
                    }

                    // CREATE o cuenta de otro landlord → rechazar
                    return Mono.<Void>error(new DuplicateBankAccountException(rawAccountNumber));
                })
                .switchIfEmpty(Mono.empty());
    }
}
