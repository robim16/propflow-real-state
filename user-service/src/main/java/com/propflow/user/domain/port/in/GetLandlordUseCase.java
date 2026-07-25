package com.propflow.user.domain.port.in;

import com.propflow.user.domain.model.Landlord;
import com.propflow.user.domain.model.vo.LandlordId;
import com.propflow.user.domain.model.vo.UserPrincipal;
import com.propflow.user.domain.port.out.LandlordRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface GetLandlordUseCase {

    Mono<Landlord> getLandlord(LandlordId landlordId, UserPrincipal userPrincipal);
    Flux<Landlord> list(LandlordRepository.LandlordQuery query, UserPrincipal userPrincipal);
}
