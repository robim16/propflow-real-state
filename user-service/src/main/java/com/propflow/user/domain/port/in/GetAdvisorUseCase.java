package com.propflow.user.domain.port.in;

import com.propflow.user.domain.model.vo.AdvisorId;
import org.springframework.aop.Advisor;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface GetAdvisorUseCase {
    Mono<Optional<Advisor>> getAdvisorById(AdvisorId advisorId);
}
