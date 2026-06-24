package com.propflow.user.domain.port.out;

import com.propflow.user.domain.model.Landlord;
import com.propflow.user.domain.model.vo.LandlordId;
import reactor.core.publisher.Mono;

public interface LandlordRepository {
    Mono<Landlord> save(Landlord landlord);
    Mono<Landlord> findById(LandlordId landlordId);
    Mono<Landlord> update(Landlord landlord);
    Mono<Boolean> existsByDocumentNumber(String documentNumber);
    Mono<Landlord> findByHashedAccountNumber(String accountNumberHash);
}
