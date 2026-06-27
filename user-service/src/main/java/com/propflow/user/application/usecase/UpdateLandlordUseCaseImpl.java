package com.propflow.user.application.usecase;

import com.propflow.user.application.usecase.validator.NonDuplicatedValidator;
import com.propflow.user.domain.model.Landlord;
import com.propflow.user.domain.port.in.UpdateLandlordCommand;
import com.propflow.user.domain.port.in.UpdateLandlordUseCase;
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

    @Override
    public Mono<Landlord> update(UpdateLandlordCommand command) {
        return landlordRepository.findById(command.landlordId())
                .flatMap(landlordRepository::update);
    }
}
