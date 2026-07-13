package com.propflow.user.application.usecase;

import com.propflow.user.application.usecase.validator.NonDuplicatedValidator;
import com.propflow.user.domain.exception.LandlordNotFoundException;
import com.propflow.user.domain.model.Landlord;
import com.propflow.user.domain.model.vo.BankAccount;
import com.propflow.user.domain.port.in.CreateLandlordCommand;
import com.propflow.user.domain.port.in.UpdateLandlordCommand;
import com.propflow.user.domain.port.in.UpdateLandlordUseCase;
import com.propflow.user.domain.port.out.CryptoPort;
import com.propflow.user.domain.port.out.LandlordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateLandlordUseCaseImpl implements UpdateLandlordUseCase {

    private final LandlordRepository landlordRepository;
    private final NonDuplicatedValidator duplicatedValidator;
    private final CryptoPort cryptoPort;

    @Override
    public Mono<Landlord> update(UpdateLandlordCommand command) {
        return landlordRepository.findById(command.landlordId())
                .switchIfEmpty(Mono.error(new LandlordNotFoundException(command.landlordId())))
                .flatMap(landlord -> validateUpdates(landlord, command))
                .then(Mono.defer(() -> buildLandlord(command)))
                .flatMap(landlordRepository::save);
    }

    private Mono<Landlord> validateUpdates(Landlord landlord, UpdateLandlordCommand command) {
        // Construimos las validaciones solo para los campos que realmente cambiaron

        Mono<Void> docValidation = command.documentNumber() != null
                // UPDATE: pasamos el landlordId existente → permite si es suyo, rechaza si es de otro
                ? duplicatedValidator.validateDocumentNotDuplicated(
                command.documentNumber(), landlord.getId())
                : Mono.empty();

        Mono<Void> accountValidation = command.bankAccountNumber() != null
                ? duplicatedValidator.validateAccountNotDuplicated(
                command.bankAccountNumber(), landlord.getId())
                : Mono.empty();

        return docValidation
                .then(accountValidation)
                .thenReturn(landlord);
    }

    private Mono<Landlord> buildLandlord(UpdateLandlordCommand command) {
        var rawNumber = command.bankAccountNumber();
        var encrypted = cryptoPort.encrypt(rawNumber);
        var hash      = cryptoPort.hash(rawNumber);
        var last4     = rawNumber.substring(rawNumber.length() - 4);

        var bankAccount = BankAccount.of(
                command.bank(),
                command.bankAccountType(),
                encrypted,
                hash,
                last4
        );

        var landlord = Landlord.create(
                command.principal().userId(),
                command.documentType(),
                command.documentNumber(),
                command.address(),
                bankAccount
        );

        return Mono.just(landlord);
    }
}
