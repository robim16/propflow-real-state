package com.propflow.user.domain.port.in;

import com.propflow.user.domain.model.Landlord;
import reactor.core.publisher.Mono;

public interface CreateLandlordUseCase {
    Mono<Landlord> createAndPersist(CreateLandlordCommand command);
}
