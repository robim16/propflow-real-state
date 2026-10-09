package com.propflow.user.domain.port.out;

import com.propflow.user.domain.model.vo.AdvisorId;
import org.springframework.aop.Advisor;
import reactor.core.publisher.Mono;

public interface AdvisorRepository {
    Mono<Advisor> findById(AdvisorId advisorId);
}
