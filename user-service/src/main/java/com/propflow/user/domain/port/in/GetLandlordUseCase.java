package com.propflow.user.domain.port.in;

import com.propflow.user.domain.model.Landlord;
import com.propflow.user.domain.model.vo.LandlordId;
import com.propflow.user.domain.model.vo.UserPrincipal;
import reactor.core.publisher.Mono;

public interface GetLandlordUseCase {
    Mono<Landlord> getLandlord(LandlordId landlordId, UserPrincipal userPrincipal);
}
