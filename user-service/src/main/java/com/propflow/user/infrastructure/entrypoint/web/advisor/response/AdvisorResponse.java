package com.propflow.user.infrastructure.entrypoint.web.advisor.response;

import com.propflow.user.domain.model.Advisor;
import com.propflow.user.domain.model.vo.ProfileStatus;


import java.time.Instant;
import java.util.Optional;

public record AdvisorResponse(
        String id,
        String userId,
        String documentType,
        String documentNumber,
        String address,
        ProfileStatus status,                // INCOMPLETE | COMPLETE
        Instant createdAt,
        Instant updatedAt
) {
    public static AdvisorResponse from(Advisor a) {
        return new AdvisorResponse(a.getId().toString(), a.getUserId().toString(),
                a.getDocumentType().toString(),
                a.getDocumentNumber(), a.getAddress(), a.getStatus(),
                a.getCreatedAt(), a.getUpdatedAt());
    }
}
