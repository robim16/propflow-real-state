package com.propflow.user.infrastructure.adapter.persistence;

import com.propflow.user.domain.model.Advisor;
import com.propflow.user.domain.model.vo.AdvisorId;
import com.propflow.user.domain.port.out.AdvisorRepository;
import com.propflow.user.infrastructure.adapter.persistence.mapper.AdvisorEntityMapper;
import com.propflow.user.infrastructure.adapter.persistence.repository.AdvisorR2dbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdvisorRepositoryAdapter implements AdvisorRepository {
    private final AdvisorR2dbcRepository r2dbcRepository;
    private final AdvisorEntityMapper mapper;

    @Override
    public Mono<Advisor> findById(AdvisorId advisorId) {
        return r2dbcRepository.findById(advisorId.value())
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Advisor> findAll(AdvisorQuery query) {
        return null;
    }
}
