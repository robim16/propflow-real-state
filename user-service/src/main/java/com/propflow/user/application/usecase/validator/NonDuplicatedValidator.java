package com.propflow.user.application.usecase.validator;

import com.propflow.user.domain.port.out.CryptoPort;
import com.propflow.user.domain.port.out.LandlordRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class NonDuplicatedValidator {

    private final LandlordRepository landlordRepository;
    private final CryptoPort cryptoPort;

    public Mono<Void> validateDocumentNotDuplicated(String documentNumber) {
        return landlordRepository.existsByDocumentNumber(documentNumber)
                .flatMap(exists -> exists
                        ? Mono.error(new IllegalStateException("El número de documento ya existe."))
                        : Mono.empty());
    }

    public Mono<Void> validateAccountNotDuplicated(String rawAccountNumber) {
        var accountNumberHash = cryptoPort.hash(rawAccountNumber);
        return landlordRepository.findByHashedAccountNumber(accountNumberHash)
                .flatMap(found -> Mono.<Void>error(
                        new IllegalStateException("El número de cuenta ya existe.")))
                .switchIfEmpty(Mono.empty());
    }
}
