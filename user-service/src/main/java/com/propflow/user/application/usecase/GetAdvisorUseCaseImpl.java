package com.propflow.user.application.usecase;

import com.propflow.user.domain.model.vo.AdvisorId;
import com.propflow.user.domain.port.in.GetAdvisorUseCase;
import com.propflow.user.domain.port.out.AdvisorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAdvisorUseCaseImpl implements GetAdvisorUseCase {
    private final AdvisorRepository advisorRepository;
    @Override
    public Mono<Optional<Advisor>> getAdvisorById(AdvisorId advisorId) {
        return advisorRepository.findById(advisorId)
                .switchIfEmpty(Mono.error(new AdvisorNotFoundException()));
    }
}
