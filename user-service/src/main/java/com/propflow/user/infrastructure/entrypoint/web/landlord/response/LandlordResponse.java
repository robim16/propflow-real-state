package com.propflow.user.infrastructure.entrypoint.web.landlord.response;

import com.propflow.user.domain.model.Landlord;
import com.propflow.user.domain.model.vo.ProfileStatus;

import java.time.Instant;

public record LandlordResponse(
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
    public static LandlordResponse from(Landlord l) {
        return new LandlordResponse(l.id(), l.userId(), l.documentType(), l.documentNumber(),
                l.address(), l.bankAccountLast4(), l, l.advisorId(), l.createdAt(), l.updatedAt());
    }
}
