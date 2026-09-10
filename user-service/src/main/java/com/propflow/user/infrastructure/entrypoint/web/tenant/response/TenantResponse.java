package com.propflow.user.infrastructure.entrypoint.web.tenant.response;

import com.propflow.user.domain.model.Tenant;
import com.propflow.user.domain.model.vo.CoDebtor;
import com.propflow.user.domain.model.vo.ProfileStatus;
import com.propflow.user.domain.model.vo.TenantReference;

import java.time.Instant;
import java.util.List;

public record TenantResponse(
        String id,
        String userId,
        String documentType,
        String documentNumber,
        List<TenantReference> references,
        CoDebtor coDebtor,
        ProfileStatus status,                // INCOMPLETE | COMPLETE
        String advisorId,
        Instant createdAt,
        Instant updatedAt
) {

    public static TenantResponse from(Tenant t) {
        return new TenantResponse(t.getId().toString(), t.getUserId().toString(), t.getDocumentType().toString(),
                t.getDocumentNumber(),t.getReferences(), t.getCoDebtor(), t.getStatus(), t.getAdvisorId(),
                t.getCreatedAt(), t.getUpdatedAt());
    }
}
