package com.propflow.user.infrastructure.adapter.persistence;

import com.propflow.user.domain.model.Landlord;
import com.propflow.user.domain.model.vo.LandlordId;
import com.propflow.user.domain.port.out.LandlordRepository;
import com.propflow.user.infrastructure.adapter.persistence.mapper.LandlordEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class LandlordRepositoryAdapter implements LandlordRepository {

    private final LandlordR2dbcRepository r2dbcRepository;
    private final LandlordEntityMapper mapper;


    @Override
    public Mono<Landlord> save(Landlord landlord) {
        return null;
    }

    @Override
    public Mono<Landlord> findById(LandlordId landlordId) {
        return null;
    }

    @Override
    public Mono<Landlord> update(Landlord landlord) {
        return null;
    }

    @Override
    public Mono<Boolean> existsByDocumentNumber(String documentNumber) {
        return r2dbcRepository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public Mono<Landlord> findByHashedAccountNumber(String accountNumberHash) {
        return r2dbcRepository.findByAccountNumberHash(accountNumberHash)
                .map(mapper::toDomain);
    }
}
