package com.propflow.user.infrastructure.entrypoint.web.advisor;

import com.propflow.user.domain.model.vo.AdvisorId;
import com.propflow.user.domain.port.in.GetAdvisorUseCase;
import com.propflow.user.infrastructure.entrypoint.web.shared.ErrorHandlingSupport;
import com.propflow.user.infrastructure.entrypoint.web.shared.PrincipalExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AdvisorHandler implements ErrorHandlingSupport {
    private final GetAdvisorUseCase getAdvisorUseCase;
    private final PrincipalExtractor principalExtractor;

    public Mono<ServerResponse> getById(ServerRequest request) {
        var advisorId = AdvisorId.of(request.pathVariable("id"));

        return withErrorHandling(
                principalExtractor.extract(request)
                        .flatMap(principal -> getAdvisorUseCase(advisorId))
        )
    }
}
