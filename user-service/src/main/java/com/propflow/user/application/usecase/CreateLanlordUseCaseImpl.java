package com.propflow.user.application.usecase;

import com.propflow.user.application.usecase.validator.NonDuplicatedValidator;
import com.propflow.user.domain.model.Landlord;
import com.propflow.user.domain.model.vo.BankAccount;
import com.propflow.user.domain.port.in.CreateLandlordCommand;
import com.propflow.user.domain.port.in.CreateLandlordUseCase;
import com.propflow.user.domain.port.out.CryptoPort;
import com.propflow.user.domain.port.out.LandlordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateLanlordUseCaseImpl implements CreateLandlordUseCase {
    private final LandlordRepository landlordRepository;
    private final CryptoPort cryptoPort;
    private final NonDuplicatedValidator duplicatedValidator;

    @Override
    public Mono<Landlord> createAndPersist(CreateLandlordCommand command) {
        return duplicatedValidator.validateDocumentNotDuplicated(command.documentNumber(), null)
                .then(duplicatedValidator.validateAccountNotDuplicated(command.bankAccountNumber(), null))
                .then(Mono.defer(() -> buildLandlord(command)))
                .flatMap(landlordRepository::save);
    }

    private Mono<Landlord> buildLandlord(CreateLandlordCommand command) {
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
                command.userId(),
                command.documentType(),
                command.documentNumber(),
                command.address(),
                bankAccount
        );

        return Mono.just(landlord);
    }
}
