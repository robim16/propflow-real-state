package com.propflow.user.infrastructure.entrypoint.web.shared;

import com.propflow.user.domain.model.vo.Role;
import com.propflow.user.domain.model.vo.UserId;
import com.propflow.user.domain.model.vo.UserPrincipal;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import reactor.core.publisher.Mono;

@Component
public class PrincipalExtractor {

    public Mono<UserPrincipal> extract(ServerRequest request) {
        return request.principal()
                .cast(JwtAuthenticationToken.class)
                .map(this::toUserPrincipal);
    }

    public Mono<UserId> extractUserId(ServerRequest request) {
        return extract(request).map(UserPrincipal::userId);
    }

    private UserPrincipal toUserPrincipal(JwtAuthenticationToken token) {
        var claims = token.getToken().getClaims();
        return new UserPrincipal(
                UserId.of((String) claims.get("userId")),
                Role.valueOf((String) claims.get("role")),
                (String) claims.get("advisorId"),
                (String) claims.get("landlordId"),
                (String) claims.get("tenantId")
        );
    }
}