package com.propflow.user.infrastructure.entrypoint.web.tenant.response;

import com.propflow.user.domain.model.Tenant;
import com.propflow.user.domain.model.vo.ProfileStatus;

import java.time.Instant;

public record TenantResponse(
        String id,
        String userId,
        String documentType,
        String documentNumber,
        String address,
        String bankAccountLast4,             // Solo últimos 4 dígitos
        ProfileStatus status,                // INCOMPLETE | COMPLETE
        String advisorId,
        Instant createdAt,
        Instant updatedAt
) {

    public static TenantResponse from(Tenant t) {
        return new TenantResponse(t.getId().toString(), t.getUserId().toString(), t.getDocumentType().toString(),
                t.getDocumentNumber(), t.getAddress(), t.getBankAccount().last4(), t.getStatus(), t.getAdvisorId(),
                t.getCreatedAt(), t.getUpdatedAt());
    }
}
