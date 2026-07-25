package com.propflow.user.infrastructure.adapter.persistence;

import com.propflow.user.domain.model.Landlord;
import com.propflow.user.domain.model.Tenant;
import com.propflow.user.domain.model.vo.LandlordId;
import com.propflow.user.domain.model.vo.UserId;
import com.propflow.user.domain.port.out.LandlordRepository;
import com.propflow.user.infrastructure.adapter.persistence.mapper.LandlordEntityMapper;
import com.propflow.user.infrastructure.adapter.persistence.repository.LandlordR2dbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class LandlordRepositoryAdapter implements LandlordRepository {

    private final LandlordR2dbcRepository r2dbcRepository;
    private final LandlordEntityMapper mapper;


    @Override
    public Mono<Landlord> save(Landlord landlord) {
        return r2dbcRepository.save(mapper.toEntity(landlord))
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Landlord> findAll(LandlordQuery query) {
        return r2dbcRepository.findAllByFilters(
                        query.advisorId(),
                        query.status(),
                        query.size(),
                        (long) query.page() * query.size()   // ← calculado aquí, no en la query
                )
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Landlord> findById(LandlordId landlordId) {
        return r2dbcRepository.findById(landlordId.value())
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Landlord> findByUserId(UserId userId) {
        return r2dbcRepository.findByUserId(userId.value())
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Landlord> findByDocumentNumber(String documentNumber) {
        return r2dbcRepository.findByDocumentNumber(documentNumber)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Landlord> findByHashedAccountNumber(String accountNumberHash) {
        return r2dbcRepository.findByAccountNumberHash(accountNumberHash)
                .map(mapper::toDomain);
    }
}
