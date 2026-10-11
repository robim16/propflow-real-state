package com.propflow.user.domain.port.out;

import com.propflow.user.domain.model.vo.AdvisorId;
import com.propflow.user.domain.model.Advisor;
import reactor.core.publisher.Mono;

public interface AdvisorRepository {
    Mono<Advisor> findById(AdvisorId advisorId);
}
