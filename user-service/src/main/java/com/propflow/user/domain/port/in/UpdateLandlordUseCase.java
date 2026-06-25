package com.propflow.user.domain.port.in;

import com.propflow.user.domain.model.Landlord;
import reactor.core.publisher.Mono;

public interface UpdateLandlordUseCase {
    Mono<Landlord> update(UpdateLandlordCommand command);
}
