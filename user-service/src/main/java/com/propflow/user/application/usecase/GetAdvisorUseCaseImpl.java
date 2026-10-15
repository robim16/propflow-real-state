package com.propflow.user.application.usecase;

import com.propflow.user.domain.exception.AdvisorNotFoundException;
import com.propflow.user.domain.model.Advisor;
import com.propflow.user.domain.model.vo.AdvisorId;
import com.propflow.user.domain.model.vo.UserPrincipal;
import com.propflow.user.domain.port.in.GetAdvisorUseCase;
import com.propflow.user.domain.port.out.AdvisorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAdvisorUseCaseImpl implements GetAdvisorUseCase {
    private final AdvisorRepository advisorRepository;
    @Override
    public Mono<Advisor> getAdvisorById(AdvisorId advisorId,  UserPrincipal principal) {
        return advisorRepository.findById(advisorId)
                .switchIfEmpty(Mono.error(new AdvisorNotFoundException(advisorId)))
                .flatMap(advisor -> validateAccess(advisor, principal));
    }

    @Override
    public Flux<Advisor> getAdvisors(UserPrincipal principal) {
        return null;
    }

    private Mono<Advisor> validateAccess(Advisor advisor, UserPrincipal principal) {
        if (principal.isAdmin()) {
            return Mono.just(advisor);
        }

        if (principal.isAdvisor() && advisor.getId() != null
            && principal.advisorId().equals(advisor.getId())) {
            return Mono.just(advisor);
        }
        return Mono.error(new AccessDeniedException("No tienes permisos para ver este perfil"));
    }
}
