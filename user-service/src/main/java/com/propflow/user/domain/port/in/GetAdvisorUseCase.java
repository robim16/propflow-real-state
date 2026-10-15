package com.propflow.user.domain.port.in;

import com.propflow.user.domain.model.vo.AdvisorId;
import com.propflow.user.domain.model.Advisor;
import com.propflow.user.domain.model.vo.UserPrincipal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface GetAdvisorUseCase {
    Mono<Advisor> getAdvisorById(AdvisorId advisorId, UserPrincipal principal);
    Flux<Advisor> getAdvisors(UserPrincipal principal);
}
