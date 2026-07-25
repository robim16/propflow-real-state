package com.propflow.user.domain.port.in;

import com.propflow.user.domain.model.vo.*;

import java.util.Objects;

public record CreateTenantCommand(
        TenantId id,
        UserId userId,
        String documentType,
        String         documentNumber,
        String          address,
        String bankAccount,
        String          advisorId
) {

    public CreateTenantCommand {
        Objects.requireNonNull(userId,            "userId es obligatorio");
        Objects.requireNonNull(documentType,      "documentType es obligatorio");
        Objects.requireNonNull(documentNumber,    "documentNumber es obligatorio");
        Objects.requireNonNull(address,           "address es obligatorio");
        Objects.requireNonNull(bankAccount,   "bankAccount es obligatorio");
        Objects.requireNonNull(advisorId, "el advisorId es obligatorio");

        if (documentNumber.isBlank()) throw new IllegalArgumentException("documentNumber vacío");
        if (address.isBlank())        throw new IllegalArgumentException("address vacío");
    }
}
