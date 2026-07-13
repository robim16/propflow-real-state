package com.propflow.user.application.usecase;

import com.propflow.user.domain.exception.LandlordNotFoundException;
import com.propflow.user.domain.model.Landlord;
import com.propflow.user.domain.model.vo.LandlordId;
import com.propflow.user.domain.model.vo.UserPrincipal;
import com.propflow.user.domain.port.in.GetLandlordUseCase;
import com.propflow.user.domain.port.out.LandlordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetLandlordUseCaseImpl implements GetLandlordUseCase {
    private final LandlordRepository landlordRepository;

    @Override
    public Mono<Landlord> getLandlord(LandlordId landlordId, UserPrincipal principal) {
        return landlordRepository.findById(landlordId)
                .switchIfEmpty(Mono.error(new LandlordNotFoundException(landlordId)))
                .flatMap(landlord -> validateAccess(landlord, principal));
    }

    private Mono<Landlord> validateAccess(Landlord landlord, UserPrincipal principal) {
        if (principal.isAdmin()) {
            return Mono.just(landlord);
        }
        if (principal.isAdvisor() && landlord.getAdvisorId() != null
                && landlord.getAdvisorId().equals(principal.advisorId())) {
            return Mono.just(landlord);
        }
        if (principal.ownsLandlordProfile(landlord.getId().value())) {
            return Mono.just(landlord);
        }
        return Mono.error(new AccessDeniedException("No tienes permisos para ver este perfil"));
    }
}
